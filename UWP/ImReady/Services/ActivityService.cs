using ImReady.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Models;
using ImReady.Services.Mock;
using ImReady.Services.Web;

namespace ImReady.Services
{
    public class ActivityService : BaseService, IActivityService
    {
        private IActivityService MockService => new MockActivityService();
        public Activity GetActivity(int ComponentId)
        {
            if(Mock)
            {
                return MockService.GetActivity(ComponentId);
            }
            else
            {
                throw new NotImplementedException();
            }
        }

        public async void CompleteActivity(Activity activity)
        {
            if(Mock)
            {
                
            }
            else
            {
                await ActivityWebService.SingleInstance.CompleteActivity(activity);
            }
        }
    }
}
