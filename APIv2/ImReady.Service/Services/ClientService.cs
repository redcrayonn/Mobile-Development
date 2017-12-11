using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public class ClientService : IClientService
    {
        private readonly IImReadyUnitOfWork unitOfWork;

        public ClientService(IImReadyUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public List<object> GetClients()
        {
            throw new NotImplementedException();
        }
    }
}
