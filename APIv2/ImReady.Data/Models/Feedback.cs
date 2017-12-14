using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    [Table("Feedback")]
    public class Feedback : EntityModel
    {
        public string CaregiverId { get; set; }
        public string ClientActivityId { get; set; }
        public DateTime Sent { get; set; }
        public string Content { get; set; }

        [ForeignKey("CaregiverId")]
        public virtual Caregiver Caregiver { get; set; }

        [ForeignKey("ClientActivityId")]
        public virtual ClientActivity ClientActivity { get; set; }
    }
}
