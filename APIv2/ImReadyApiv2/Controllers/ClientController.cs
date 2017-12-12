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
    public class ClientController : BaseApiController
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
            var clientResult = new ClientUserResult(client);
            clientResult.Roles = await _userManager.GetRolesAsync(client.Id);
            return Ok(clientResult);
        }

        // POST: api/Client
        public async Task<IHttpActionResult> Post([FromBody]PostUserInputModel model)
        {
            var user = model.GetUser<Client>();

            Validate<User>(user);

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            
            var result = await _userManager.CreateAsync(user, model.Password);
            if (result.Succeeded)
            {
                result = await _userManager.AddToRoleAsync(user.Id, Role.CLIENT.ToString());
                if (result.Succeeded)
                {
                    user = _clientService.GetClient(user.Id);
                    return Ok(user);
                }
            }

            return BadRequest("could not create the user or assign the role");
        }

        // PUT: api/Client/5
        public void Put(int id, [FromBody]Client value)
        {
        }

        // DELETE: api/Client/5
        public void Delete(int id)
        {
        }
    }
}
