using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class ClientTask
    {
        public string Name { get; set; }
        public string DeadlineDate { get; set; }
        public string Description { get; set; }
        public int Status { get; set; }
        public string Feedback { get; set; }
        public string Id { get; set; }
    }
}
