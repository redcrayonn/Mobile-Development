using ImReady.Models;
using ImReady.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.ViewModels
{
    public class HomeMainViewModel
    {
        public static HomeMainViewModel SingleInstance => new HomeMainViewModel();

        public List<BuildingBlock> Blocks { get; set; }

        public BuildingBlock AddBlock = new BuildingBlock()
        {
            Image = "ms-appx:///Assets/Material/ic_add_white_48dp.png",
            Name = "",
            BuildingBlockType = BuildingBlockType.Add,
        };

        public HomeMainViewModel()
        {
            Blocks = new BuildingBlockMockService().GetAllBlocks();
            Blocks.Add(AddBlock);
        }
    }
}
