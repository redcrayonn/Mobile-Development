using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models;
using ImReadyApiv2.Context;
using ImReadyApiv2.Viewmodels;

namespace ImReadyApiv2.Services
{
    public class FutureplanService : IFutureplanService
    {
        private ImReadyDbContext _dbContext;

        public FutureplanService()
        {
            _dbContext = new ImReadyDbContext();
        }

        public FutureplanViewModel GetPlan(string clientId)
        {
            var blocks = _dbContext.ClientBuildingblocks
                            .Where(w => w.ClientId == clientId && !w.Deleted).ToList();
            return new FutureplanViewModel(blocks);
        }
    }
}