using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results.FutureplanResult
{
    public class FeedbackResult : BaseResult
    {
        public FeedbackResult(Feedback feedback) : base(feedback)
        {
            Content = feedback.Content;
            CaregiverName = feedback.Caregiver.FirstName;
        }
        public string Content { get; set; }

        public string CaregiverName { get; set; }
    }
}