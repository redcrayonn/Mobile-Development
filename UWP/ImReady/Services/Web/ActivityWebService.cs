using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;
using ImReady.Models.InputModels;

namespace ImReady.Services.Web
{
    public class ActivityWebService : BaseService
    {
        public static ActivityWebService SingleInstance => new ActivityWebService();

        public async Task<Activity> CompleteActivity(Activity activity)
        {
            if (CurrentUser.SingleInstance.IsLoggedIn)
            {
                var user = CurrentUser.SingleInstance;
                var futureUrl = $"client/{user.Id}/activity/{activity.Id}";

                var uri = apiMainUrl + futureUrl;
                var httpMethod = HttpMethod.Put;

                var parameters = new Dictionary<string, string>()
                    {
                        { "id", user.Id },
                        { "activityId", activity.Id },
                        { "value", JsonConvert.SerializeObject(new ActivityInputModel() { Status = 1, Content = activity.Content })}
                    };

                return await BaseClient.HandleAsync<Activity>(uri, httpMethod);
            }
            else
            {
                //TODO: throw need to login error/message & send to login page.
                return null;
            }
        }
    }
}
