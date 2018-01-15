using ImReady.Data.Enums;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models;
using System.ComponentModel.DataAnnotations;

namespace ImReadyApiv2.Models.Input
{
    public class PostBuildingblockInputModel
    {
		[Required]
        public string Name { get; set; }

		[Required]
		public string Description { get; set; }

		[Required]
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