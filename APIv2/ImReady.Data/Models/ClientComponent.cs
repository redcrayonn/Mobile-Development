using ImReady.Data.Enums;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    [Table("ClientComponent")]
    public class ClientComponent : EntityModel
    {
        public string Description { get; set; }
        public Guid ClientBuildingBlockId { get; set; }
        public DateTime Deadline { get; set; }
        public Status Status { get; set; }

        [ForeignKey("ClientBuildingBlockId")]
        public virtual ClientBuildingBlock ClientBuildingBlock { get; set; }

        public Guid TaskId { get; set; }

        [ForeignKey("TaskId")]
        public virtual Task Task { get; set; }

    }
}
