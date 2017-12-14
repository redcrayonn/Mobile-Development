using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results
{
    public class BuildingblockResult : BaseResult
    {
        public BuildingblockResult(Buildingblock block) :base(block)
        {
            Name = block.Name;
            Description = block.Description;
            Type = block.Type;

            Components = new List<ComponentResult>();
            foreach (var component in block.Components)
            {
                if (!component.Deleted)
                {
                    Components.Add(new ComponentResult(component));
                }
            }
        }
        public string Name { get; }

        public string Description { get; }

        public BlockType Type { get; }

        public List<ComponentResult> Components { get; }
    }
}