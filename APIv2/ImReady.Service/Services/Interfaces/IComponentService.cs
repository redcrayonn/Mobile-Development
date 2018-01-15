using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services.Interfaces {
	public interface IComponentService {
		Component Get (string componentId);
		void Create (Component component);
		bool Update (Component component);
	}
}
