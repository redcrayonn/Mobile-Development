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

        public async Task<LoginResult> Login(string userName, string password)
        {
            var loginUrl = "";

            var user = CurrentUser.SingleInstance;
            var uri = apiMainUrl + loginUrl;
            var httpMethod = HttpMethod.Post;
            var parameters = new Dictionary<string, string>()
                    {
                        { "UserName", userName },
                        { "Password", password}
                    };

            var result = await BaseClient.HandleAsync<LoginResult>(uri, httpMethod, parameters);

            if (result != null && !string.IsNullOrEmpty(result.AuthToken))
            {
                var vault = new Windows.Security.Credentials.PasswordVault();
                if (!vault.RetrieveAll().Any())
                {
                    var credentials = new Windows.Security.Credentials.PasswordCredential(Package.Current.DisplayName, userName, password);
                    vault.Add(credentials);
                }
                user.AuthToken = result.AuthToken;
                user.Username = userName;
                return result;
            }
            return result;
        }
    }
}
