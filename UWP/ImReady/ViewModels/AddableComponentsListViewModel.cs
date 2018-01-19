using ImReady.Models;
using ImReady.Repositories;
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
                //Filter de lijst met componenten zodat alleen de nog niet gekoppelde componenten overblijven
                List<Component> addAbleComponents = new List<Component>();
                var existingComponents = FuturePlanRepo.GetAllCachedComponents();
                foreach (var component in Components)
                {
                    if (!existingComponents.Any(c => c.SubComponent.Id == component.Id))
                        addAbleComponents.Add(component);
                }
                return addAbleComponents;
            }
        }
    }
}
