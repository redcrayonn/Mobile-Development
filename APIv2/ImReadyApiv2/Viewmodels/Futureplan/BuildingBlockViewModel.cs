using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results
{
    public class BuildingBlockViewModel : BaseResult
    {
        public BuildingBlockViewModel(ClientBuildingBlock block) : base(block)
        {
            Name = block.Block.Name;
            Description = block.Block.Description;
            Type = block.Block.Type;

            Components = new List<ComponentViewModel>();
            foreach (var component in block.Components)
            {
                Components.Add(new ComponentViewModel(component));
            }
        }

        public string Name { get; set; }

        public string Description { get; set; }

        public BlockType Type { get; set; }

        public List<ComponentViewModel> Components { get; set; }

    }
}