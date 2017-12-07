using ImReady.Data.Enums;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace ImReady.Data.Models
{
    [Table("ClientTask")]
    public class Task : EntityModel
    {
        public string Name { get; set; }

        public DateTime DeadlineDate { get; set; }

        public string Content { get; set; }

        public Status Status { get; set; }

        public string Feedback { get; set; }
    }
}