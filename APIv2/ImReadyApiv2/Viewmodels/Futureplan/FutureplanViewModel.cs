using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Viewmodels
{
    public class FutureplanViewModel
    {
        public FutureplanViewModel(List<ClientBuildingBlock> blocks)
        {

        }

        public string Name { get; set; }

        public string Description { get; set; }

        public BlockType Type { get; set; }

        public List<ComponentViewModel> Components { get; set; }
    }
}