using ImReady.Helpers.Commands;
using ImReady.Models;
using ImReady.Services;
using ImReady.Views.BlockComponents;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace ImReady.ViewModels
{
    public class HomeMainViewModel
    {
        public static HomeMainViewModel SingleInstance => new HomeMainViewModel();

        public RelayCommand NavigateToBuildingBlockComponents => new RelayCommand(NavigateToComponents);

        public List<BuildingBlock> Blocks { get; set; }

        public BuildingBlock AddBlock = new BuildingBlock()
        {
            Image = "ms-appx:///Assets/Material/ic_add_white_48dp.png",
            Name = "",
            Type = (int)BuildingBlockType.Add,
        };

        public HomeMainViewModel()
        {
            Blocks = new BuildingBlockMockService().GetAllBlocks();
            Blocks.Add(AddBlock);
        }

        public static void NavigateToComponents(object obj)
        {
            if(obj != null && obj is BuildingBlock)
            {
                var item = obj as BuildingBlock;
                ((Frame)Window.Current.Content).Navigate(typeof(BuildingBlockComponents), item);
            }
        }
    }
}
