using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services.Web
{
    public class FuturePlanWebService : BaseService
    {
        public static FuturePlanWebService SingleInstance => new FuturePlanWebService();

        //Todo create service in order to mock
        public async Task<FuturePlan> GetFuturePlan()
        {

            if(CurrentUser.SingleInstance.IsLoggedIn)
            {
                var user = CurrentUser.SingleInstance;
                var futureUrl = $"client/{user.Id}/futureplan";

                var uri = apiMainUrl + futureUrl;
                var httpMethod = HttpMethod.Get;

                //var parameters = new Dictionary<string, string>()
                //    {
                //        { "id", user.Id },
                //    };

                return await BaseClient.HandleAsync<FuturePlan>(uri, httpMethod);
            }
            else
            {
                //TODO: throw need to login error/message & send to login page.
                return null;
            }
        }
    }
}
