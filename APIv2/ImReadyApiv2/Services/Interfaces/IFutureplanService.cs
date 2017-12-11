using ImReady.Data.Models;
using ImReadyApiv2.Results;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReadyApiv2.Services
{
    public interface IFutureplanService
    {
        FutureplanViewModel GetPlan(string clientId);
    }
}
