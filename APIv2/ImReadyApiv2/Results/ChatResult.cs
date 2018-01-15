using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results {
	public class ChatResult : BaseResult {
		public string SenderId { get; set; }
		public string ReceiverId { get; set; }
		public List<MessageResult> Messages { get; set; }

		public ChatResult (Chat chat) : base(chat) {
			SenderId = chat.SenderId;
			ReceiverId = chat.ReceiverId;
			Messages = new List<MessageResult>();

			foreach(Message message in chat.Messages) {
				MessageResult messageResult = new MessageResult(message);
				Messages.Add(messageResult);
			}
		}
	}
}