﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace ImReady.Models
{
    public class Activity
    {
        //Custom props

        //Json props
        public string Name { get; set; }
        public string Description { get; set; }
        public int Points { get; set; }
        public int Status { get; set; }
        public string Content { get; set; }
        public string Deadline { get; set; }
        public Feedback[] Feedback { get; set; }
        [JsonProperty("Activity")]
        public Activity SubActivity { get; set; }
        public string Id { get; set; }
    }
}
