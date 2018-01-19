using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services.Web
{
    public class ComponentWebService : BaseService
    {
        public static ComponentWebService SingleInstance => new ComponentWebService();

        //Todo create service in order to mock
        public async Task<BuildingBlock[]> GetAllBlocks()
        {

            if (CurrentUser.SingleInstance.IsLoggedIn)
            {
                var user = CurrentUser.SingleInstance;
                var blockUrl = $"buildingblock";

                var uri = apiMainUrl + blockUrl;
                var httpMethod = HttpMethod.Get;

                //var parameters = new Dictionary<string, string>()
                //    {
                //        { "id", user.Id },
                //    };

                return await BaseClient.HandleAsync<BuildingBlock[]>(uri, httpMethod);
            }
            else
            {
                //TODO: throw need to login error/message & send to login page.
                return null;
            }
        }

        public async Task<bool> AddComponent(Component component)
        {
            if (CurrentUser.SingleInstance.IsLoggedIn)
            {
                var user = CurrentUser.SingleInstance;
                var blockUrl = $"client/{user.Id}/component/{component.Id}";

                var uri = apiMainUrl + blockUrl;
                var httpMethod = HttpMethod.Post;

                //var parameters = new Dictionary<string, string>()
                //    {
                //        { "id", user.Id },
                //    };

                return await BaseClient.HandleAsync<bool>(uri, httpMethod);
            }
            else
            {
                //TODO: throw need to login error/message & send to login page.
                return false;
            }
        }
    }
}
