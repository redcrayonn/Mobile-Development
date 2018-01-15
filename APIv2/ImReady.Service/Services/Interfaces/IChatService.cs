using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services.Interfaces {
	public interface IChatService {
		List<Chat> getChats (string userId);
		Chat getChat (string userId, string otherUserId);
		bool addMessage (string chatId, Message message);
		Chat createChat (string userId, string otherUserId);
	}
}
