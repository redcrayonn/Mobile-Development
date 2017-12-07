using ImReady.Config;
using ImReady.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services
{
   public class BaseService : IBaseService
    {
       public bool Mock => GlobalConfig.MockServices;
    }
}
