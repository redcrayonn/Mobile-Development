using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.ViewModels
{
    public class AddableComponentsListViewModel
    {
        public static AddableComponentsListViewModel SingleInstance => new AddableComponentsListViewModel();

        public List<Component> Components { get; set; }

        public List<Component> FilteredComponents
        {
            get
            {
                return Components.Where(c => c.Activities.Any()).ToList();
            }
        }
    }
}
