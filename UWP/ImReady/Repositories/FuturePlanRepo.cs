using ImReady.Models;
using ImReady.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Repositories
{
    public class FuturePlanRepo
    {
        public static FuturePlan CachedFuturePlan { get; set; }
    }
}
