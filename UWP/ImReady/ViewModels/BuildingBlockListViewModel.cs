using ImReady.Helpers.Commands;
using ImReady.Models;
using ImReady.Repositories;
using ImReady.Services;
using ImReady.Views.AddComponent;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Documents;

namespace ImReady.ViewModels
{
    public class BuildingBlockListViewModel : INotifyPropertyChanged
    {
        public static BuildingBlockListViewModel SingleInstance => new BuildingBlockListViewModel();

        public RelayCommand NavigateToAddComponent => new RelayCommand(NavToAddComponent);

        public static void NavToAddComponent(object obj)
        {
            ((Frame)Window.Current.Content).Navigate(typeof(BuildingBlockList));
        }

        public BuildingBlockListViewModel()
        {
            this.LoadBlocks();
            //this.Blocks = new List<BuildingBlock>()
            //{
            //    new BuildingBlock()
            //    {
            //        Name = "test"
            //    }
            //};
        }

        public event PropertyChangedEventHandler PropertyChanged;

        private List<BuildingBlock> blocks;

        public List<BuildingBlock> Blocks
        {
            get
            {
                return blocks;
            }
            set
            {
                blocks = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("Blocks"));
            }
        }

        public async void LoadBlocks()
        {
            BuildingBlock[] unfilteredBlocks = await new BuildingBlockComponentService().GetAllBuildingBlocks();
            var tempBlocks = unfilteredBlocks.ToList();
            FuturePlan futurePlan;
            if (FuturePlanRepo.CachedFuturePlan != null)
                futurePlan = FuturePlanRepo.CachedFuturePlan;
            else
                futurePlan = await new FuturePlanService().GetFuturePlan();
            var futurePlanBlocks = futurePlan.Blocks.ToList();
            List<BuildingBlock> finalBlocks = new List<BuildingBlock>();
            foreach (var oriBlock in unfilteredBlocks)
            {
                foreach(var oriComponent in oriBlock.Components)
                {
                    if (!futurePlanBlocks.Exists(c => c.Components.Exists(d => d.Id == oriComponent.Id)))
                    {
                        if(oriComponent.Activities.Any())
                            finalBlocks.Add(oriBlock);
                    }
                }
            }
            Blocks = finalBlocks.Distinct().ToList();
        }
    }
}
