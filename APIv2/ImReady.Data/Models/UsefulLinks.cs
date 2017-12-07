using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace ImReady.Data.Models
{
    [Table("UsefulLink")]
    public class UsefulLink : EntityModel
    {
        public Guid ComponentId { get; set; }

        public string Url { get; set; }

        [ForeignKey("ComponentId")]
        public virtual Component Component { get; set; }
    }
}