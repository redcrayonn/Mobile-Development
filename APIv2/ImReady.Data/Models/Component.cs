using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    [Table("Component")]
    public class Component : EntityModel
    {
        public Guid BuildingblockId { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }

        public string YoutubeURL { get; set; }

        public virtual ICollection<UsefulLink> UsefulLinks { get; set; }

        [ForeignKey("BuildingblockId")]
        public virtual Buildingblock Block { get; set; }
    }
}
