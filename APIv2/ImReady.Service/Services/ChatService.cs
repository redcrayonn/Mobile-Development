using ImReady.Service.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models;
using Patterns.Repository;
using ImReady.Data.Models.Users;

namespace ImReady.Service.Services {
	public class ChatService : IChatService {
		private readonly IImReadyUnitOfWork _unitOfWork;
		private readonly IRepository<Chat> _chatRepository;

		public ChatService (IImReadyUnitOfWork unitOfWork) {
			_unitOfWork = unitOfWork;

			_chatRepository = _unitOfWork.ChatRepository;
		}

		public bool addMessage (string chatId, Message message) {
			Chat chat = _chatRepository.Entities.SingleOrDefault(c => c.Id == chatId);

			if (chat == null) {
				return false;
			}

			chat.Messages.Add(message);
			_unitOfWork.Commit();

			return true;
		}

		public Chat createChat (string userId, string otherUserId) {
			Chat chat = new Chat();
			chat.SenderId = userId;
			chat.ReceiverId = otherUserId;
			chat.Messages = new List<Message>();
			_chatRepository.Add(chat);
			_unitOfWork.Commit();

			return chat;
		}

		public Chat getChat (string userId, string otherUserId) {
			return _chatRepository.Entities.SingleOrDefault(c => (c.SenderId == userId && c.ReceiverId == otherUserId) || (c.SenderId == otherUserId && c.ReceiverId == userId));
		}

		public List<Chat> getChats (string userId) {
			return _chatRepository.Entities.Where(c => c.SenderId == userId || c.ReceiverId == userId).ToList();
		}
	}
}
