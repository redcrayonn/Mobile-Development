using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input {
	public class PutClientTaskInputModel {

		[Required]
		public string Name { get; set; }

		[Required]
		[DataType(DataType.DateTime)]
		public DateTime DeadlineDate { get; set; }
		
		[Required]
		public string Description { get; set; }

		[Required]
		public Status Status { get; set; }

		public string Feedback { get; set; }

		internal ClientTask GetModel () {
			ClientTask task = new ClientTask();
			task.Name = Name;
			task.DeadlineDate = DeadlineDate;
			task.Description = Description;
			task.Status = Status;
			task.Feedback = Feedback;

			return task;
		}
	}
}