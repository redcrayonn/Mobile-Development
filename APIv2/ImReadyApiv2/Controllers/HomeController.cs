using ImReady.Service.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ImReadyApiv2.Controllers
{
    public class HomeController : ApiController
    {
        private readonly IClientService clientService;

        public HomeController(IClientService clientService)
        {
            this.clientService = clientService;
        }

        public IHttpActionResult Get()
        {
            return Ok("hello world");
        }
    }
}
