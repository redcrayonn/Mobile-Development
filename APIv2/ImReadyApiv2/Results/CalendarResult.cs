using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results {
	public class CalendarResult : BaseResult {
		public string Title { get; }

		public DateTime StartDate { get; }

		public DateTime EndDate { get; }

		public String Location { get; }

		public String Remark { get; }

		public CalendarResult (Calendar calendar) : base(calendar) {
			Title = calendar.Title;
			StartDate = calendar.StartDate;
			EndDate = calendar.EndDate;
			Location = calendar.Location;
			Remark = calendar.Remark;
		}
	}
}