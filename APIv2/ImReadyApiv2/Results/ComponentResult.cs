using ImReady.Data.Models;
using System.Collections.Generic;

namespace ImReadyApiv2.Results
{
    public class ComponentResult : BaseResult
    {
        public ComponentResult(Component component) : base(component)
        {
            Name = component.Name;
            Description = component.Description;
            Activities = new List<ActivityResult>();
            foreach (var activity in component.Activities)
            {
                if (!activity.Deleted)
                {
                    Activities.Add(new ActivityResult(activity));
                }
            }
        }
        public string Name { get; }
        public string Description { get; }
        public List<ActivityResult> Activities { get; }
    }
}