using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReadyApiv2.Services
{
    public interface IFutureplanService
    {
        List<ClientBuildingBlock> GetPlan(string id);
    }
}
