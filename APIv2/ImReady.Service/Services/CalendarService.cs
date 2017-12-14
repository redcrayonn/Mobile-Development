using ImReady.Service.Services.Interfaces;
using System.Collections.Generic;
using System.Linq;
using ImReady.Data.Models;
using Patterns.Repository;

namespace ImReady.Service.Services {
	public class CalendarService : ICalendarService {
		private readonly IImReadyUnitOfWork _unitOfWork;
		private readonly IRepository<Calendar> _calendarRepository;

		public CalendarService (IImReadyUnitOfWork unitOfWork) {
			_unitOfWork = unitOfWork;

			_calendarRepository = _unitOfWork.CalendarRepository;
		}

		public List<Calendar> Get (string userId) {
			var calendarItems =_calendarRepository.Entities.Where(c => c.UserId == userId).ToList();

			//if (calendarItems == null) {
			//	calendarItems = new List<Calendar>();
			//}

			return calendarItems;
		}
	}
}
