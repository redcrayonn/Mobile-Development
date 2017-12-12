using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReady.Service.Services;
using ImReadyApiv2.Context;
using ImReadyApiv2.Models;
using ImReadyApiv2.Models.Input;
using ImReadyApiv2.Results;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;

namespace ImReadyApiv2.Controllers
{
    public class ClientController : ApiController
    {
        private readonly IClientService _clientService;

        public ClientController(IClientService clientService)
        {
            _clientService = clientService;
        }

        // GET: api/Client/5
        public async Task<IHttpActionResult> Get(string id)
        {
            var client = _clientService.GetClient(id);
            if (client == null)
            {
                return NotFound();
            }
            var clientResult = new UserResult(client);
            return Ok(clientResult);
        }

        // POST: api/Client
        public async void Post([FromBody]PostUserInputModel model)
        {
            var user = model.GetUser<Client>();
            // sanitize user role (prevents overposting roles)
            user.Roles.Clear();

            var context = Request.GetOwinContext().Get<ImReadyDbContext>();
            var store = new UserStore<User>();
            var userManager = new ApplicationUserManager(store);
            var result = await userManager.CreateAsync(user, model.Password);
            if (result.Succeeded)
            {
                result = await userManager.AddToRoleAsync(user.Id, Role.CLIENT.ToString());
            }
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
