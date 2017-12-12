using ImReady.Data.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models;

namespace ImReadyApiv2.Models.Input
{
    public class PostBuildingblockInputModel
    {
        public string Name { get; set; }
        public string Description { get; set; }
        public BlockType Type { get; set; }

        internal Buildingblock GetBlock()
        {
            return new Buildingblock
            {
                Deleted = false,
                Description = Description,
                Name = Name,
                Type = Type,
            };
        }
    }
}