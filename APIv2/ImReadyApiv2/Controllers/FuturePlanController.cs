using ImReadyApiv2.Services;
using ImReadyApiv2.Viewmodels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
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
        // GET: api/User/5
        public IHttpActionResult Get(string clientId)
        {
            FutureplanViewModel futureplan = _planService.GetPlan(clientId);

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
