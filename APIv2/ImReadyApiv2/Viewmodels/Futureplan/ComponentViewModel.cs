using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Viewmodels
{
    public class ComponentViewModel : BaseEntityViewModel
    {
        public ComponentViewModel(ClientComponent component) : base(component)
        {
            Name = component.Component.Name;
            Description = component.Component.Description;
            Status = component.Status;

            Activities = new List<ActivityViewModel>();
            foreach (var activity in component.Activities)
            {
                Activities.Add(new ActivityViewModel(activity));
            }
            //TODO DEADLINE
        }

        public string Name { get; set; }

        public string Description { get; set; }

        public Status Status { get; set; }

        public List<ActivityViewModel> Activities { get; set; }

        public DateTime Deadline { get; set; }
    }
}