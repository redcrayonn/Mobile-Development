using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReady.Service.Services.Interfaces;
using ImReadyApiv2.Models.Input;
using ImReadyApiv2.Results;
using ImReadyApiv2.Services.Interfaces;
using System.Collections.Generic;
using System.Web.Http;
using System.Web.Http.Description;

namespace ImReadyApiv2.Controllers {
	[RoutePrefix("api/user")]
	public class ChatController : BaseApiController {

		private readonly IUserService _userService;
		private readonly IChatService _chatService;

		/// <summary>
		/// ctor
		/// </summary>
		/// <param name="userService">Service class for user business logic</param>
		/// <param name="chatService">Service class for chat business logic</param>
		public ChatController (IUserService userService, IChatService chatService) {
			_userService = userService;
			_chatService = chatService;
		}

		/// <summary>
		/// Return all chats for user
		/// </summary>
		/// <remarks>Gets all chats for the specified user</remarks>
		/// <response code="200">OK</response>
		/// <response code="404">Not found</response>
		[ResponseType(typeof(List<ChatResult>))]
		[Route("{userId}/chats")]
		public IHttpActionResult Get (string userId) {
			User user = _userService.GetUser(userId);

			if (user == null) {
				return NotFound();
			}

			List<Chat> chats = _chatService.getChats(userId);

			List<ChatResult> chatsResult = new List<ChatResult>();
			foreach (Chat chat in chats) {
				ChatResult chatResult = new ChatResult(chat);
				chatsResult.Add(chatResult);
			}

			return Ok(chatsResult);
		}

		/// <summary>
		/// Return a chat
		/// </summary>
		/// <remarks>Get chat where sender/receiver correspond with the userId/otherUserId</remarks>
		/// <response code="200">OK</response>
		/// <response code="404">Not found</response>
		[ResponseType(typeof(ChatResult))]
		[Route("{userId}/chat/{otherUserId}")]
		public IHttpActionResult Get (string userId, string otherUserId) {
			User user = _userService.GetUser(userId);
			User otherUser = _userService.GetUser(otherUserId);

			if (user == null || otherUser == null) {
				return NotFound();
			}

			Chat chat = _chatService.getChat(userId, otherUserId);

			if (chat == null) {
				chat = _chatService.createChat(userId, otherUserId);
			}

			return Ok(new ChatResult(chat));
		}

		/// <summary>
		/// Add message to chat
		/// </summary>
		/// <remarks>Add a message to a chat. The chat is determined via given sender/receiver which correspond with the userId/otherUserId</remarks>
		/// <response code="200">OK</response>
		/// <response code="404">Not found</response>
		[ResponseType(typeof(ChatResult))]
		[Route("{userId}/chat/{otherUserId}")]
		public IHttpActionResult Post (string userId, string otherUserId, [FromBody]PostMessageInputModel model) {
			Validate(model);

			if (!ModelState.IsValid) {
				return BadRequest(ModelState);
			}

			Chat chat = _chatService.getChat(userId, otherUserId);

			if (chat == null) {
				return NotFound();
			}

			Message message = model.GetModel(chat, userId);
			bool isSuccess = _chatService.addMessage(chat.Id, message);

			return Ok(new MessageResult(message));
		}

		//TODO An endpoint to tell if a chatMessage is read
	}
}