using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.ViewModels
{
    public class AddLowerComponentViewModel
    {
        public static AddLowerComponentViewModel SingleInstance => new AddLowerComponentViewModel();

        public List<Component> Components { get; set; }

    }
}
