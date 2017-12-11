using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;

namespace ImReadyApiv2.Results
{
    public class ActivityViewModel : BaseResult
    {
        public ActivityViewModel(ClientActivity activity) : base(activity)
        {
            Name = activity.Activity.Name;
            Description = activity.Activity.Description;
            Points = activity.Activity.Points;
            Status = activity.Status;
            Content = activity.Content;

            Feedback = new List<FeedbackViewModel>();
            foreach (var feedback in activity.Feedback)
            {
                Feedback.Add(new FeedbackViewModel(feedback));
            }
            //Deadline = activity.Deadline;
        }
        public string Name { get; set; }

        public string Description { get; set; }

        public int Points { get; set; }

        public Status Status { get; set; }

        public string Content { get; set; }

        public DateTime Deadline { get; set; }

        public List<FeedbackViewModel> Feedback { get; set; }
    }
}