using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results
{
    public class FutureplanViewModel
    {
        public FutureplanViewModel(List<ClientBuildingBlock> blocks)
        {
            Blocks = new List<BuildingBlockViewModel>();
            foreach (var block in blocks)
            {
                Blocks.Add(new BuildingBlockViewModel(block));
            }
        }
        public List<BuildingBlockViewModel> Blocks { get; set; }
        
    }
}