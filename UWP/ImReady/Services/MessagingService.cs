using ImReady.Models;
using ImReady.Services.Web;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services
{
    public class MessagingService : BaseService
    {
        public async Task<Chat> GetChat(string senderId, string receiverId)
        {
            return await new MessagingWebService().GetChat(senderId, receiverId);
        }
    }
}
