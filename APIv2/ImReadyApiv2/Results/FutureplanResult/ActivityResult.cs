using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results.FutureplanResult
{
    public class ActivityResult : BaseResult
    {
        public ActivityResult(ClientActivity activity) : base(activity)
        {
            Name = activity.Activity.Name;
            Description = activity.Activity.Description;
            Points = activity.Activity.Points;
            Status = activity.Status;
            Content = activity.Content;
            Deadline = activity.Deadline;
            Activity = new Results.ActivityResult(activity.Activity);

            Feedback = new List<FeedbackResult>();
            foreach (var feedback in activity.Feedback)
            {
                Feedback.Add(new FeedbackResult(feedback));
            }
        }
        public string Name { get; set; }

        public string Description { get; set; }

        public int Points { get; set; }

        public Status Status { get; set; }

        public string Content { get; set; }

        public DateTime Deadline { get; set; }

        public List<FeedbackResult> Feedback { get; set; }

        public Results.ActivityResult Activity { get; set; }
    }
}