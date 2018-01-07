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
            UsefulLinks = new List<UsefulLinkResult>();
            foreach (var activity in component.Activities)
            {
                if (!activity.Deleted)
                {
                    Activities.Add(new ActivityResult(activity));
                }
            }
            foreach (var link in component.UsefulLinks)
            {
                UsefulLinks.Add(new UsefulLinkResult(link));
            }
        }
        public string Name { get; }
        public string Description { get; }
        public List<ActivityResult> Activities { get; }
        public List<UsefulLinkResult> UsefulLinks { get; }
    }
}