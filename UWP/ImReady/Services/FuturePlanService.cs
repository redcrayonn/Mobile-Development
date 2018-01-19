using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Models;
using ImReady.Repositories;
using ImReady.Services.Web;

namespace ImReady.Services
{
    public class FuturePlanService : BaseService
    {
        //private IFuturePlanService MockService => new MockFuturePlanService();
        public async Task<FuturePlan> GetFuturePlan()
        {
            if (Mock)
            {
                //return MockService.GetActivity(ComponentId);
                throw new NotImplementedException();
            }
            else
            {
                FuturePlan FuturePlan = await FuturePlanWebService.SingleInstance.GetFuturePlan();
                FuturePlanRepo.CachedFuturePlan = FuturePlan;
                return FuturePlan;
            }
        }
    }
    
}
