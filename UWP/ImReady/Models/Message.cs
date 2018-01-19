using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class Message
    {
        public string Content { get; set; }
        public string SentDate { get; set; }
        public bool Read { get; set; }
        public string SenderId { get; set; }
        public string Id { get; set; }
    }
}
