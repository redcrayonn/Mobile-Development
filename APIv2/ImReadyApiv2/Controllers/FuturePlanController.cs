using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReady.Service.Services;
using ImReady.Service.Services.Interfaces;
using ImReadyApiv2.Results.FutureplanResult;
using System.Net;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Http.Results;

namespace ImReadyApiv2.Controllers
{
    public class FuturePlanController : ApiController
    {
        private readonly IFutureplanService _planService;
		private readonly IClientService _clientService;
		private readonly IBuildingblockService _buildingBlockService;
		private readonly IClientBuildingBlockService _clientBuildingBlockService;

		/// <summary>
		/// ctor
		/// </summary>
		/// <param name="planService"></param>
		/// <param name="clientService"></param>
		/// <param name="clientService"></param>
		/// <param name="clientService"></param>
		public FuturePlanController (IFutureplanService planService, 
			IClientService clientService, 
			IBuildingblockService buildingBlockService, 
			IClientBuildingBlockService clientBuildingBlockService) {
            _planService = planService;
			_clientService = clientService;
			_buildingBlockService = buildingBlockService;
			_clientBuildingBlockService = clientBuildingBlockService;
        }

        /// <summary>
        /// Returns the futureplan of the client
        /// </summary>
        /// <param name="id">Id of the Client</param>
        /// <response code="200">OK</response>
        /// <response code="500">InternalServerError</response>
        [Route("api/client/{id}/futureplan")]
        [ResponseType(typeof(FutureplanResult))]
        public IHttpActionResult Get(string id)
        {
            FutureplanResult futureplan = new FutureplanResult(_planService.GetPlan(id));

            if (futureplan != null)
            {
                return Ok(futureplan);
            }
            else
            {
                return new StatusCodeResult(HttpStatusCode.InternalServerError, this);
            }
        }

		/// <summary>
		/// Enroll to client to a new building block
		/// </summary>
		/// <param name="id">Id of the Client</param>
		/// <param name="buildingBlockId">Id of the building block</param>
		/// <response code="204">No content</response>
		/// <response code="500">InternalServerError</response>
		[Route("api/client/{id}/futureplan/enroll/{buildingBlockId}")]
		[ResponseType(typeof(FutureplanResult))]
		public IHttpActionResult Post (string id, string buildingBlockId) {
			Client client = _clientService.GetClient(id);
			Buildingblock buildingBlock = _buildingBlockService.getById(buildingBlockId);

			if (client == null || buildingBlock == null) {
				return NotFound();
			}

			bool isSuccess = _clientBuildingBlockService.Enroll(id, buildingBlockId);

			if (!isSuccess) {
				return BadRequest("Could not enroll the client");
			}

			return StatusCode(HttpStatusCode.NoContent);
		}
	}
}
