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

        public HomeMainViewModel()
        {
            Blocks = new BuildingBlockMockService().GetBlocks();
        }
    }
}
