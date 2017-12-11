using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results.FutureplanResult
{
    public class FutureplanResult
    {
        public FutureplanResult(List<ClientBuildingBlock> blocks)
        {
            Blocks = new List<BuildingBlockResult>();
            foreach (var block in blocks)
            {
                Blocks.Add(new BuildingBlockResult(block));
            }
        }
        public List<BuildingBlockResult> Blocks { get; set; }
    }
}