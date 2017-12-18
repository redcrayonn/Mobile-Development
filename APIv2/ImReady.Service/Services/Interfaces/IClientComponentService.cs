using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public interface IClientComponentService
    {
        bool Enroll(string clientId, string componentId);
		ClientComponent Get (string componentId);
    }
}
