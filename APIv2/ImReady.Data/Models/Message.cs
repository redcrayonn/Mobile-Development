using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    [Table("Message")]
    public class Message : EntityModel
    {
        public Guid ChatId { get; set; }
        public String Content { get; set; }
        public DateTime SentDate { get; set; }
        public bool Read { get; set; }
    }
}
