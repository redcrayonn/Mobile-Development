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

        public FuturePlanController(IFutureplanService planService)
        {
            _planService = planService;
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
    }
}
