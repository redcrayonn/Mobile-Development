using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class Client
    {
        public int Points { get; set; }

        public string CaregiverId { get; set; }

        public Caregiver Caregiver { get; set; }
    }
}
