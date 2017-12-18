using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results {
	public class ClientTaskResult : BaseResult{
		public string Name { get; set; }
		public DateTime DeadlineDate { get; set; }
		public string Description { get; set;}
		public Status Status { get; set; }
		public string Feedback { get; set; }

		public ClientTaskResult(ClientTask task) : base(task) {
			Name = task.Name;
			DeadlineDate = task.DeadlineDate;
			Description = task.Description;
			Status = task.Status;
			Feedback = task.Feedback;
		}
	}
}