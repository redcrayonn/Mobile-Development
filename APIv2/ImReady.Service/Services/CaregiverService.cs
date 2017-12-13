using ImReady.Service.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models.Users;
using Patterns.Repository;

namespace ImReady.Service.Services
{
    public class CaregiverService : ICaregiverService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<Caregiver> _caregiverRepository;
        private readonly IRepository<Client> _clientRepository;

        public CaregiverService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _caregiverRepository = _unitOfWork.CaregiverRepository;
            _clientRepository = _unitOfWork.ClientRepository;
        }

        public Caregiver GetCaregiver(string id)
        {
            var client = _caregiverRepository.Entities.FirstOrDefault(c => c.Id == id);
            return client;
        }

        public bool AssignToCaregiver(string caregiverId, string clientId)
        {
            var caregiver = _caregiverRepository.Entities.FirstOrDefault(c => c.Id == caregiverId);
            var client = _clientRepository.Entities.FirstOrDefault(c => c.Id == clientId);

            if (caregiver == null || client == null)
            {
                // either the caregiver or client could not be found
                return false;
            }

            if (caregiver.Clients.Any(c => c.Id == client.Id))
            {
                // client has already been added
                return false;
            }

            caregiver.Clients.Add(client);
            _unitOfWork.Commit();

            return true;
        }
    }
}
