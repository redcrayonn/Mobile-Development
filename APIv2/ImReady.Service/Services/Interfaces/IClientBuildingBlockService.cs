using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services.Interfaces {
	public interface IClientBuildingBlockService {
		bool Enroll (string clientId, string buildingBlockId);
	}
}
