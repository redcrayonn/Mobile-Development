using ImReady.Data.Enums;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    public class ClientComponent : EntityModel
    { 
        public Guid ClientBuildingBlockId { get; set; }

        [ForeignKey("ClientBuildingBlockId")]
        public ClientBuildingBlock ClientBuildingBlock { get; set; }

        public Status Status { get; set; }
    }
}
