using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public interface IClientService
    {
        List<Client> GetClients();
        List<Client> GetClients(string id);
        Client GetClient(string id);
    }
}
