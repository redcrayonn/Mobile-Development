﻿using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static ImReady.Helpers.StaticHelpers;

namespace ImReady.Models
{
    public class Component
    {
        //Custom props
        public string ActivityCount => $"{Activities.Count()} activiteiten";
        public string DeadlineInDays => DeadlineToDaysRemaining(Deadline);
        public string TotalPointsAvailable
        {
            get
            {
                int points = 0;
                foreach(var activity in Activities)
                {
                    points = +activity.Points;
                }
                return points.ToString();
            }
        }
        public string ActivitiesInText {
            get
            {
                string result = "";
                foreach(var activity in Activities)
                {
                    result += $"- {activity.Name}\n";
                }
                return result;
            }
        }
        //Json props
        public string Name { get; set; }
        public string Description { get; set; }
        public int Status { get; set; }
        public Activity[] Activities { get; set; }
        public ClientTask[] Tasks { get; set; }
        public DateTime Deadline { get; set; }
        [JsonProperty("Component")]
        public Component SubComponent { get; set; }
        public string Id { get; set; }
        public UsefulLink[] UsefulLinks { get; set; }



    }
}
