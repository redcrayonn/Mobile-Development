using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace ImReady.Data.Models
{
    [Table("ClientUsefulLink")]
    public class ClientUsefulLink : EntityModel
    {
        public string ClientComponentId { get; set; }

        public string Url { get; set; }

        [ForeignKey("ClientComponentId")]
        public virtual ClientComponent ClientComponent { get; set; }
    }
}