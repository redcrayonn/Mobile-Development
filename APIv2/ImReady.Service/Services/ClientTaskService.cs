using ImReady.Service.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models;
using Patterns.Repository;

namespace ImReady.Service.Services {
	public class ClientTaskService : IClientTaskService {
		private readonly IImReadyUnitOfWork _unitOfWork;
		private readonly IRepository<ClientTask> _clientTaskRepository;
		private readonly ICalendarService _calendarService;

		public ClientTaskService (IImReadyUnitOfWork unitOfWork, ICalendarService calendarService) {
			_unitOfWork = unitOfWork;
			_clientTaskRepository = unitOfWork.ClientTaskRepository;
			_calendarService = calendarService;
		}

		public void Create (string clientId, ClientTask task) {
			Calendar calendar = new Calendar();
			calendar.UserId = clientId;
			calendar.Title = task.Name;
			calendar.StartDate = task.DeadlineDate;
			calendar.EndDate = calendar.StartDate;

			_calendarService.CreateCalendarItem(calendar);

			task.Calendar = calendar;

			_clientTaskRepository.Add(task);
			_unitOfWork.Commit();
		}

		public void Delete (string clientId, ClientTask task) {
			Calendar calendar = _calendarService.GetCalendarItem(clientId, task.CalendarId);
			_calendarService.DeleteCalendarItem(calendar);

			_clientTaskRepository.Remove(task);
			_unitOfWork.Commit();
		}

		public ClientTask Get (string id) {
			return _clientTaskRepository.Entities.SingleOrDefault(t => t.Id == id);
		}

		public List<ClientTask> GetAll () {
			throw new NotImplementedException();
		}

		public void Update (string clientId, ClientTask task) {
			ClientTask editTask = _clientTaskRepository.Entities.SingleOrDefault(t => t.Id == task.Id);

			editTask.Name = task.Name;
			editTask.DeadlineDate = task.DeadlineDate;
			editTask.Description = task.Description;
			editTask.Status = task.Status;
			editTask.Feedback = (task.Feedback != null) ? task.Feedback : editTask.Feedback;

			Calendar calendar = _calendarService.GetCalendarItem(clientId, editTask.CalendarId);
			calendar.Title = task.Name;
			calendar.StartDate = task.DeadlineDate;
			calendar.EndDate = calendar.StartDate;

			_unitOfWork.Commit();
		}
	}
}
