using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public interface IClientActivityService
    {
        ICollection<ClientActivity> getAll(string componentId);
        ClientActivity getById(string Id);
        bool AddActivity(ClientActivity activity);
        void ChangeStatus(string Id, Status status);
        bool RemoveActivity(string Id);
        bool EditActivity(ClientActivity activity);

    }
}
