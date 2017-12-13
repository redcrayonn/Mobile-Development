using ImReady.Data.Enums;
using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    public class Notification : EntityModel
    {
        public string ReceiverId { get; set; }
        [ForeignKey("ReceiverId")]
        public User Receiver { get; set; }

        public string SenderId { get; set; }
        [ForeignKey("SenderId")]
        public User Sender { get; set; }

        public bool IsRead { get; set; }
        public DateTime CreatedDate { get; set; }
        public NotificationType Type { get; set; }
        public string ObjectId { get; set; }
    }
}
