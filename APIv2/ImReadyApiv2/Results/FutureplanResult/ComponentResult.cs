using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results.FutureplanResult
{
    public class ComponentResult : BaseResult
    {
        public ComponentResult(ClientComponent component) : base(component)
        {
            Name = component.Component.Name;
            Description = component.Component.Description;
            Status = component.Status;
            Deadline = component.Deadline;

            Activities = new List<ActivityResult>();
            foreach (var activity in component.Activities)
            {
                Activities.Add(new ActivityResult(activity));
            }
        }

        public string Name { get; set; }

        public string Description { get; set; }

        public Status Status { get; set; }

        public List<ActivityResult> Activities { get; set; }

        public DateTime Deadline { get; set; }
    }
}