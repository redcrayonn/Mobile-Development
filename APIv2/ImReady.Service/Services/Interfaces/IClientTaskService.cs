using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services.Interfaces {
	public interface IClientTaskService {
		List<ClientTask> GetAll ();
		ClientTask Get (string id);
		void Create (ClientTask task);
		void Update (ClientTask task);
		void Delete (ClientTask task);
	}
}
