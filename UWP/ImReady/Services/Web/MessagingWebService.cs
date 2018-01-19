using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services.Web
{
    public class MessagingWebService : BaseService
    {
        public static MessagingWebService SingleInstance => new MessagingWebService();

        public async Task<Chat> GetChat(string senderId, string receiverId)
        {
            var chatUrl = $"user/{senderId}/chat/{receiverId}";

            var uri = apiMainUrl + chatUrl;
            var httpMethod = HttpMethod.Get;

            //var parameters = new Dictionary<string, string>()
            //    {
            //        { "Status", ((int)ActivityStatus.PENDING).ToString() },
            //        { "Content", activity.Content }
            //    };

            return await BaseClient.HandleAsync<Chat>(uri, httpMethod);
        }

        public async Task<Message> SendMessage(string senderId, string receiverId, string content)
        {
            var chatUrl = $"user/{senderId}/chat/{receiverId}";

            var uri = apiMainUrl + chatUrl;
            var httpMethod = HttpMethod.Post;

            var parameters = new Dictionary<string, string>()
                {
                    { "Content", content},
                };

            return await BaseClient.HandleAsync<Message>(uri, httpMethod, parameters);
        }
    }
}
