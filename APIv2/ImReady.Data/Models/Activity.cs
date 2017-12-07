using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    [Table("Activity")]
    public class Activity : EntityModel
    {
        public string Name { get; set; }

        public string Description { get; set; }

        public int Points { get; set; }

        public Guid ComponentId { get; set; }

        [ForeignKey("ComponentId")]
        public virtual Component Component { get; set; }
    }
}
