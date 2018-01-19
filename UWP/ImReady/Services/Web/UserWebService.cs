using ImReady.Models;
using ImReady.Models.Results;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel;

namespace ImReady.Services.Web
{
    public class UserWebService : BaseService
    {
        public static UserWebService SingleInstance => new UserWebService();

        //Todo create service in order to mock
        public async Task<LoginResult> Login(string userName, string password)
        {
            var loginUrl = "login";

            var user = CurrentUser.SingleInstance;
            var uri = apiMainUrl + loginUrl;
            var httpMethod = HttpMethod.Post;
            var parameters = new Dictionary<string, string>()
                    {
                        { "UserName", userName },
                        { "Password", password },
                        { "grant_type", "password" }
                    };

            var result = await BaseClient.HandleAsync<LoginResult>(uri, httpMethod, parameters);

            if (result != null && !string.IsNullOrEmpty(result.access_token))
            {
                //var vault = new Windows.Security.Credentials.PasswordVault();
                //if (!vault.RetrieveAll().Any())
                //{
                //    //password moet authtoken worden!!!
                //    //Access token is tijdelijk geldig
                //    //Refresh token: om nieuwe access token op te halen
                //    var credentials = new Windows.Security.Credentials.PasswordCredential(Package.Current.DisplayName, userName, result.access_token);
                //    vault.Add(credentials);
                //}
                user.AccessToken = result.access_token;
                user.Username = userName;
                result.UserName = userName;
                user.Id = result.user_id;
                //TODO: aanvullen, wss functie maken
                return result;
            }
            return result;
        }

        public async Task<Client> GetClient(string id)
        {
            var clientUrl = "client";

            var uri = apiMainUrl + clientUrl + $"/{id}";
            var httpMethod = HttpMethod.Get;
            //var parameters = new Dictionary<string, string>()
            //        {
            //            { "UserName", userName },
            //            { "Password", password },
            //            { "grant_type", "password" }
            //        };

            var result = await BaseClient.HandleAsync<Client>(uri, httpMethod);
            return result;
        }
    }
}
