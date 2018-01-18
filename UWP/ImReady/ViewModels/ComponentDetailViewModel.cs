using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.ViewModels
{
    public class ComponentDetailViewModel
    {
        public static ComponentDetailViewModel SingleInstance => new ComponentDetailViewModel();

        public Component Component { get; set; }
    }
}
