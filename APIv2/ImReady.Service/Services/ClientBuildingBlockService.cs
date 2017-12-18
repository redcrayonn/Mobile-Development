using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReady.Service.Services.Interfaces;
using Patterns.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Unity.Interception.Utilities;

namespace ImReady.Service.Services {
	public class ClientBuildingBlockService : IClientBuildingBlockService {
		private readonly IImReadyUnitOfWork _unitOfWork;
		private readonly IRepository<ClientBuildingBlock> _clientbuildingBlockRepository;
		private readonly IClientService _clientService;
		private readonly IBuildingblockService _buildingBlockService;
		private readonly IClientComponentService _clientComponentService;

		public ClientBuildingBlockService (IImReadyUnitOfWork unitOfWork, IClientService clientService, IBuildingblockService buildingBlockService, IClientComponentService clientComponentService) {
			_unitOfWork = unitOfWork;

			_clientbuildingBlockRepository = _unitOfWork.ClientBuildingblockRepository;
			_clientService = clientService;
			_buildingBlockService = buildingBlockService;
			_clientComponentService = clientComponentService;
		}

		public bool Enroll (string clientId, string buildingBlockId) {
			Client client = _clientService.GetClient(clientId);
			Buildingblock buildingBlock = _buildingBlockService.getById(buildingBlockId);

			if (client == null || buildingBlockId == null) {
				return false;
			}

			ClientBuildingBlock clientBuildingBlock = _clientbuildingBlockRepository.Entities.SingleOrDefault(cb => cb.ClientId == clientId && cb.BuildingblockId == buildingBlockId);

			if (clientBuildingBlock != null) {
				return false;
			}

			clientBuildingBlock = new ClientBuildingBlock();
			clientBuildingBlock.Client = client;
			clientBuildingBlock.Block = buildingBlock;
			clientBuildingBlock.Components = new List<ClientComponent>();

			_clientbuildingBlockRepository.Add(clientBuildingBlock);
			_unitOfWork.Commit();

			buildingBlock.Components.ForEach(c => _clientComponentService.Enroll(clientId, c.Id));

			return true;
		}
	}
}
