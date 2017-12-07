using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    [Table("ClientBuildingBlock")]
    public class ClientBuildingBlock : EntityModel
    {
        public Guid BuildingblockId { get; set; }

        public Guid ClientId { get; set; }

        [ForeignKey("BuildingblockId")]
        public virtual Buildingblock Block { get; set; }

        [ForeignKey("ClientId")]
        public virtual Client Client { get; set; }
    }
}
