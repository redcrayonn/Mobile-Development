using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results.FutureplanResult
{
    public class ClientComponentResult : BaseResult
    {
        public ClientComponentResult(ClientComponent component) : base(component)
        {
            Name = component.Component.Name;
            Description = component.Description;
			YoutubeURL = component.YoutubeUrl;
            Status = component.Status;
            Deadline = component.Deadline;
            Component = new Results.ComponentResult(component.Component);

            Activities = new List<ActivityResult>();
            foreach (var activity in component.Activities)
            {
                Activities.Add(new ActivityResult(activity));
            }

			UsefulLinks = new List<UsefulLinkResult>();
			foreach (var link in component.UsefulLinks) {
				UsefulLinks.Add(new UsefulLinkResult(link));
			}

			Tasks = new List<ClientTaskResult>();
			foreach(var task in component.ClientTasks) {
				Tasks.Add(new ClientTaskResult(task));
			}
        }

        public string Name { get; set; }

        public string Description { get; set; }

		public string YoutubeURL { get; }

		public Status Status { get; set; }

        public List<ActivityResult> Activities { get; set; }

		public List<UsefulLinkResult> UsefulLinks { get; }

		public List<ClientTaskResult> Tasks { get; set; }

		public DateTime Deadline { get; set; }

        public Results.ComponentResult Component { get; set; }
    }
}