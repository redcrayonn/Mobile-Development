using ImReady.Service.Services.Interfaces;
using System.Collections.Generic;
using System.Linq;
using ImReady.Data.Models;
using Patterns.Repository;
using System;

namespace ImReady.Service.Services {
	public class CalendarService : ICalendarService {
		private readonly IImReadyUnitOfWork _unitOfWork;
		private readonly IRepository<Calendar> _calendarRepository;

		public CalendarService (IImReadyUnitOfWork unitOfWork) {
			_unitOfWork = unitOfWork;

			_calendarRepository = _unitOfWork.CalendarRepository;
		}
		
		public List<Calendar> GetCalendarItems (string userId) {
			return _calendarRepository.Entities.Where(c => c.UserId == userId).ToList();
		}

		public Calendar getCalendarItem (string userId, string calendarId) {
			return _calendarRepository.Entities.SingleOrDefault(c => c.UserId == userId && c.Id == calendarId);
		}

		public void CreateCalendarItem (Calendar calendar) {
			_calendarRepository.Add(calendar);
			_unitOfWork.Commit();
		}

		public void DeleteCalendarItem (Calendar calendar) {
			_calendarRepository.Remove(calendar);
			_unitOfWork.Commit();
		}
	}
}
