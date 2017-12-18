using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services.Interfaces {
	public interface ICalendarService {
		List<Calendar> GetCalendarItems(string userId);
		Calendar GetCalendarItem (string userId, string calendarId);
		void CreateCalendarItem (Calendar calendar);
		void DeleteCalendarItem (Calendar calendar);
		void DeleteRelatedCalendarItem (string id);
	}
}
