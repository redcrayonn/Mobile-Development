using ImReady.Data.Enums;
using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using Patterns.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public class ClientComponentService : IClientComponentService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<ClientComponent> _clientComponentRepository;
        private readonly IRepository<ClientBuildingBlock> _clientBuildingblockRepository;
        private readonly IRepository<Component> _componentRepository;
        private readonly IRepository<Client> _clientRepository;

        public ClientComponentService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _clientComponentRepository = _unitOfWork.ClientComponentRepository;
            _clientBuildingblockRepository = _unitOfWork.ClientBuildingblockRepository;
            _clientRepository = _unitOfWork.ClientRepository;
            _componentRepository = _unitOfWork.ComponentRepository;
        }

        public bool Enroll(string clientId, string componentId)
        {
            var client = _clientRepository.Entities.FirstOrDefault(c => c.Id == clientId);
            var component = _componentRepository.Entities.FirstOrDefault(x => x.Id == componentId);

            if (client == null || component == null)
            {
                return false;
            }
            var block = component.Block;

            var clientBuildingBlock = _clientBuildingblockRepository.Entities.FirstOrDefault(b => b.Id == block.Id && b.ClientId == clientId);
            if (clientBuildingBlock == null)
            {
                clientBuildingBlock = new ClientBuildingBlock
                {
                    Block = block,
                    Client = client
                };
            }
            
            var clientComponent = new ClientComponent
            {
                Component = component,
                Status = Status.ONGOING,
                ClientBuildingBlock = clientBuildingBlock,
                Deadline = DateTime.Today.AddDays(20),
                Activities = component.Activities.Select(a => new ClientActivity
                {
                    Status = Status.ONGOING,
                    Activity = a,
                    Deadline = DateTime.Today.AddDays(14)
                }).ToList()
            };

            _clientComponentRepository.Add(clientComponent);
            _unitOfWork.Commit();
            return true;
        }
    }
}
