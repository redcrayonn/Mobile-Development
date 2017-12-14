using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models.Users;
using Patterns.Repository;

namespace ImReady.Service.Services
{
    public class ClientService : IClientService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<Client> _clientRepository;

        public ClientService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _clientRepository = _unitOfWork.ClientRepository;
        }

        public List<Client> GetClients()
        {
            return _clientRepository.Entities.ToList();
        }

        public Client GetClient(string id)
        {
            var client = _clientRepository.Entities.FirstOrDefault(c => c.Id == id);
            return client;
        }

        /// <summary>
        /// Gets all the clients of the caregiver
        /// </summary>
        /// <param name="id">Id of caregiver</param>
        public List<Client> GetClients(string id)
        {
            return _clientRepository.Entities.Where(x=>x.CaregiverId==id).ToList();
        }
    }
}
