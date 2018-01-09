﻿using ImReady.Helpers.Commands;
using ImReady.Models;
using ImReady.Services;
using ImReady.Views.BlockComponents;
using ImReady.Views.BuildingBlockComponents;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace ImReady.ViewModels
{
    public class HomeMainViewModel : INotifyPropertyChanged
    {
        public static HomeMainViewModel SingleInstance => new HomeMainViewModel();

        public RelayCommand NavigateToBuildingBlockComponents => new RelayCommand(NavigateToComponents);

        public RelayCommand NavigateToAddComponent => new RelayCommand(NavigateToAddComponents);

        public event PropertyChangedEventHandler PropertyChanged;

        private FuturePlan futurePlan;

        public FuturePlan FuturePlan
        {
            get
            {
                return futurePlan;
            }
            set
            {
                futurePlan = value;
                PropertyChanged?.Invoke(this, new PropertyChangedEventArgs("FuturePlan"));
            }
        }

        public BuildingBlock AddBlock = new BuildingBlock()
        {
            Image = "ms-appx:///Assets/Material/ic_add_white_48dp.png",
            Name = "",
            Type = (int)BuildingBlockType.Add,
        };

        public HomeMainViewModel()
        {
            LoadFuturePlan();
        }

        public static void NavigateToComponents(object obj)
        {
            if(obj != null && obj is BuildingBlock)
            {
                var item = obj as BuildingBlock;
                ((Frame)Window.Current.Content).Navigate(typeof(BuildingBlockComponents), item);
            }
        }

        public static void NavigateToAddComponents(object obj)
        {
            ((Frame)Window.Current.Content).Navigate(typeof(AddBuildingBlockComponents));
        }

        public async void LoadFuturePlan()
        {
            var futurePlanConcept = await new FuturePlanService().GetFuturePlan();
            if(futurePlanConcept == null)
            {
                futurePlanConcept = new FuturePlan()
                {
                    Blocks = new BuildingBlock[] { }
                };
            }
            var blockList = futurePlanConcept.Blocks.ToList();
            blockList.Add(AddBlock);
            futurePlanConcept.Blocks = blockList.ToArray();
            FuturePlan = futurePlanConcept;
        }
    }
}
