using ImReady.Service.Services.Interfaces;
using ImReadyApiv2.Results.FutureplanResult;
using System.Net;
using System.Web.Http;
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

        //Todo Authorisation
        // GET: api/Futureplan/5
        public IHttpActionResult Get(string clientId)
        {
            FutureplanResult futureplan = new FutureplanResult(_planService.GetPlan(clientId));

            if (futureplan != null)
            {
                return Ok(futureplan);
            }
            else
            {
                return new StatusCodeResult(HttpStatusCode.InternalServerError, this);
            }
        }

        // GET: api/Futureplan/5
        public IHttpActionResult Get()
        {
            //Get Id of logged in client

            FutureplanResult futureplan = new FutureplanResult(_planService.GetPlan("1"));

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
