using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results.FutureplanResult
{
    public class BuildingBlockResult : BaseResult
    {
        public BuildingBlockResult(ClientBuildingBlock block) : base(block)
        {
            Name = block.Block.Name;
            Description = block.Block.Description;
            Type = block.Block.Type;
            Block = new Results.BuildingBlockResult(block.Block, true);

            Components = new List<ClientComponentResult>();
            foreach (var component in block.Components)
            {
                Components.Add(new ClientComponentResult(component));
            }
        }

        public string Name { get; set; }

        public string Description { get; set; }
        public Results.BuildingBlockResult Block { get; set; }
        public BlockType Type { get; set; }

        public List<ClientComponentResult> Components { get; set; }
    }
}