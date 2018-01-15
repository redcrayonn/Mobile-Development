using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input {
	public class PostMessageInputModel {
		[Required]
		public string Content { get; set; }

		[Required]
		[DataType(DataType.DateTime)]
		public DateTime SentDate { get; set; }

		public bool Read { get; set; }

		internal Message GetModel(Chat chat, string senderId) {
			Message message = new Message();
			message.Chat = chat;
			message.ChatId = chat.Id;
			message.Content = Content;
			message.SentDate = SentDate;
			message.Read = Read;
			message.UserId = senderId;

			return message;
		}
	}
}