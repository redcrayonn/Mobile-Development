using ImReady.Data.Enums;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    [Table("Buildingblock")]
    public class Buildingblock : EntityModel
    {
        public string Name { get; set; }

        public string Description { get; set; }

        public BlockType Type { get; set; }
    }
}
