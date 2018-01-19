using ImReady.Models;
using ImReady.Services.Web;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services
{
    public class ClientService : BaseService
    {
        public async Task<Client> GetClient(string id)
        {
            return await UserWebService.SingleInstance.GetClient(id);
        }
    }
}
