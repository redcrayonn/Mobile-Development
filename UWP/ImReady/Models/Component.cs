﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace ImReady.Models
{
    public class Component
    {
        //Custom props
        public string DeadlineInDays => $"Over {(Deadline - DateTime.Now).TotalDays} dagen";

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
