using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class BuildingBlockComponent
    {
        public string ComponentName { get; set; }
        public List<Activity> Activities { get; set; }
    }
}
