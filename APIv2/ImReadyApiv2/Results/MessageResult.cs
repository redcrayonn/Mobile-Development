using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results {
	public class MessageResult : BaseResult{
		public string Content { get; }
		public DateTime SentDate { get; }
		public bool Read { get; }
		public string SenderId { get; }

		public MessageResult(Message message) : base(message) {
			Content = message.Content;
			SentDate = message.SentDate;
			Read = message.Read;
			SenderId = message.UserId;
		}
	}
}