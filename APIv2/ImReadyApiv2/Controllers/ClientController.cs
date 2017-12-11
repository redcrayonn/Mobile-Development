using ImReady.Service.Services;
using ImReadyApiv2.Results;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace ImReadyApiv2.Controllers
{
    [Authorize]
    public class ClientController : ApiController
    {
        private readonly IClientService _clientService;

        public ClientController(IClientService clientService)
        {
            _clientService = clientService;
        }

        // GET: api/Client/5
        public IHttpActionResult Get(string id)
        {
            var client = _clientService.GetClient(id);
            if (client == null)
            {
                NotFound();
            }
            var clientResult = new UserResult(client);
            return Ok(clientResult);
        }

        // POST: api/Client
        public void Post([FromBody]string value)
        {
        }

        // PUT: api/Client/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE: api/Client/5
        public void Delete(int id)
        {
        }
    }
}
