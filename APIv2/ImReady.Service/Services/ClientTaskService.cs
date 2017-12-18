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

		public ClientTaskService (IImReadyUnitOfWork unitOfWork) {
			_unitOfWork = unitOfWork;
			_clientTaskRepository = unitOfWork.ClientTaskRepository;
		}

		public void Create (ClientTask task) {
			_clientTaskRepository.Add(task);
			_unitOfWork.Commit();
		}

		public void Delete (ClientTask task) {
			_clientTaskRepository.Remove(task);
			_unitOfWork.Commit();
		}

		public ClientTask Get (string id) {
			return _clientTaskRepository.Entities.SingleOrDefault(t => t.Id == id);
		}

		public List<ClientTask> GetAll () {
			throw new NotImplementedException();
		}

		public void Update (ClientTask task) {
			ClientTask editTask = _clientTaskRepository.Entities.SingleOrDefault(t => t.Id == task.Id);

			editTask.Name = task.Name;
			editTask.DeadlineDate = task.DeadlineDate;
			editTask.Description = task.Description;
			editTask.Status = task.Status;
			editTask.Feedback = (task.Feedback != null) ? task.Feedback : editTask.Feedback;

			_unitOfWork.Commit();
		}
	}
}
