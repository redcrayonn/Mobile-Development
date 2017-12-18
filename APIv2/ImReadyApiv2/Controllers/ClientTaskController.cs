using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReady.Service.Services;
using ImReady.Service.Services.Interfaces;
using ImReadyApiv2.Models.Input;
using ImReadyApiv2.Results;
using ImReadyApiv2.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Http.Description;

namespace ImReadyApiv2.Controllers {

	/// <summary>
	/// Api controller for clientTask calls
	/// </summary>
	[RoutePrefix("api/client")]
	public class ClientTaskController : BaseApiController{

        private readonly IUserService _userService;
		private readonly IClientTaskService _clientTaskService;
		private readonly IClientComponentService _clientComponentService;
		private readonly IClientBuildingBlockService _clientBuildingBlockService;

		/// <summary>
		/// ctor
		/// </summary>
		/// <param name="userService">Service class for user business logic</param>
		/// <param name="clientTaskService">Service class for clientTask business logic</param>
		/// <param name="clientComponentService">Service class for client component business logic</param>
		/// <param name="clientBuildingBlockService">Service class for client buildingblock business logic</param>
		public ClientTaskController (	IUserService userService, 
										IClientTaskService clientTaskService, 
										IClientComponentService clientComponentService, 
										IClientBuildingBlockService clientBuildingBlockService) {
			_userService = userService;
			_clientTaskService = clientTaskService;
			_clientComponentService = clientComponentService;
			_clientBuildingBlockService = clientBuildingBlockService;
		}

		[Route("{clientId}/buildingblock/{buildingBlockId}")]
		public IHttpActionResult Post (string clientId, string buildingBlockId) {
			var result = _clientBuildingBlockService.Enroll(clientId, buildingBlockId);

			if (!result) {
				return BadRequest($"Could not enroll client: {clientId} for buildingBlock: {buildingBlockId}!");
			}

			return StatusCode(HttpStatusCode.NoContent);
		}

		/// <summary>
		/// Returns all client task items
		/// </summary>
		/// <remarks>Gets all client tasks for the specified user</remarks>
		/// <response code="200">OK</response>
		/// <response code="404">Not Found</response>
		[ResponseType(typeof(List<ClientTask>))]
		[Route("{clientId}/task")]
		public IHttpActionResult Get (string clientId) {
			User user = _userService.GetUser(clientId);
			if (user == null) {
				return NotFound();
			}

			List<ClientTask> tasks = _clientTaskService.GetAll();

			return Ok(tasks);
		}

		/// <summary>
		/// Returns a client task
		/// </summary>
		/// <remarks>Get specific client task for the specified user</remarks>
		/// <response code="200">OK</response>
		/// <response code="404">Not Found</response>
		[ResponseType(typeof(ClientTask))]
		[Route("{clientId}/task/{taskId}")]
		public IHttpActionResult Get (string clientId, string taskId) {
			User user = _userService.GetUser(clientId);
			if(user == null) {
				return NotFound();
			}

			ClientTask task = _clientTaskService.Get(taskId);
			if (task == null) {
				return NotFound();
			}

			return Ok(task);
		}

		/// <summary>
		/// Create a client task
		/// </summary>
		/// <remarks>Create a client task for the specified user</remarks>
		/// <response code="200">OK</response>
		/// <response code="404">Not Found</response>
		[ResponseType(typeof(ClientTaskResult))]
		[Route("{clientId}/component/{componentId}/task")]
		public IHttpActionResult Post (string clientId, string componentId, [FromBody]PostClientTaskInputModel model) {
			User user = _userService.GetUser(clientId);
			
			if (user == null) {
				return NotFound();
			}

			ClientTask task = model.GetModel();

			Validate(task);
			
			if (!ModelState.IsValid) {
				return BadRequest(ModelState);
			}

			ClientComponent component = _clientComponentService.Get(componentId);

			if (component == null) {
				return NotFound();
			}

			task.ClientComponent = component;

			_clientTaskService.Create(task);
			
			return Ok(new ClientTaskResult(task));
		}
		
		/// <summary>
		/// Update a client task
		/// </summary>
		/// <remarks>Update specific client task for the specified user</remarks>
		/// <response code="200">OK</response>
		/// <response code="404">Not Found</response>
		[ResponseType(typeof(ClientTaskResult))]
		[Route("{clientId}/task/{taskId}")]
		public IHttpActionResult Put (string clientId, string taskId, [FromBody]PutClientTaskInputModel model) {
			User user = _userService.GetUser(clientId);
			if (user == null) {
				return NotFound();
			}

			ClientTask task = model.GetModel();

			Validate(task);

			if (!ModelState.IsValid) {
				return BadRequest(ModelState);
			}

			ClientTask editTask = _clientTaskService.Get(taskId);

			if (editTask == null) {
				return NotFound();
			}

			task.Id = editTask.Id;

			_clientTaskService.Update(task);

			return Ok(new ClientTaskResult(editTask));
		}

		/// <summary>
		/// Delete a client task
		/// </summary>
		/// <remarks>Delete specific client task for the specified user</remarks>
		/// <response code="204">OK</response>
		/// <response code="404">Not Found</response>
		[Route("{clientId}/task/{taskId}")]
		public IHttpActionResult Delete (string clientId, string taskId) {
			User user = _userService.GetUser(clientId);
			ClientTask task = _clientTaskService.Get(taskId);

			if (user == null || task == null) {
				return NotFound();
			}

			_clientTaskService.Delete(task);

			return StatusCode(HttpStatusCode.NoContent);
		}
	}
}