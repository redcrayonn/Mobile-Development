﻿using ImReady.Data.Models.Users;
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
        public string ChatId { get; set; }
        [ForeignKey("ChatId")]
        public Chat Chat { get; set; }

        public string Content { get; set; }
        public DateTime SentDate { get; set; }
        public bool Read { get; set; }

        public string UserId { get; set; }

        [ForeignKey("UserId")]
        public virtual User Sender { get; set; }
    }
}
