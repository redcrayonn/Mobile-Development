using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input
{
    public class PutCaregiverActivityInputModel
    {
        public bool Approved { get; set; }
        public string Feedback { get; set; }
    }
}