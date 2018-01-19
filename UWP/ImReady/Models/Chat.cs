using ImReady.Views.Messages;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class Chat
    {
        public string SenderId { get; set; }
        public string ReceiverId { get; set; }
        public Message[] Messages { get; set; }
        public string Id { get; set; }
    }
}
