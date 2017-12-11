using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;

namespace ImReadyApiv2.Viewmodels
{
    public class ActivityViewModel : EntityModel
    {
        public string Name { get; set; }

        public string Description { get; set; }

        public int Points { get; set; }

        public Status Status { get; set; }

        public string Content { get; set; }

        public DateTime Deadline { get; set; }

        public virtual ICollection<FeedbackViewModel> Feedback { get; set; }
    }
}