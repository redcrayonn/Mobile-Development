using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReadyApiv2.Attributes;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input {
	public class PostCalendarInputModel {
		
		[Required]
		public string Title { get; set; }

		[Required]
		[DataType(DataType.DateTime)]
		public DateTime StartDate { get; set; }

		[DataType(DataType.DateTime)]
		[CompareIfWithinADay("StartDate")]
		public DateTime EndDate { get; set; }

		public string Location { get; set; }

		public string Remark { get; set; }
		
		[Required]
		public string ClientId { get; set; }

		internal Calendar getModel (User user) {
			Calendar calendar = new Calendar();
			calendar.UserId = user.Id;
			calendar.User = user;
			calendar.Title = Title;
			calendar.StartDate = StartDate;
			calendar.EndDate = EndDate;
			calendar.Location = Location;
			calendar.Remark = Remark;

			return calendar;
		}
	}
}