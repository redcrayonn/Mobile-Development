using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReady.Service.Services.Interfaces;
using ImReadyApiv2.Models.Input;
using ImReadyApiv2.Results;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;

namespace ImReadyApiv2.Controllers
{
    [RoutePrefix("api/caregiver")]
    public class CaregiverController : BaseApiController
    {
        private readonly ICaregiverService _caregiverService;

        public CaregiverController(ICaregiverService userService)
        {
            _caregiverService = userService;
        }

        // GET: api/Caregiver/5
        public async Task<IHttpActionResult> Get(string id)
        {
            var caregiver = _caregiverService.GetCaregiver(id);
            if (caregiver == null)
            {
                return NotFound();
            }
            var caregiverResult = new CaregiverUserResult(caregiver, true);
            caregiverResult.Roles = await _userManager.GetRolesAsync(caregiver.Id);
            return Ok(caregiverResult);
        }

        // POST: api/Caregiver
        public async Task<IHttpActionResult> Post([FromBody]PostUserInputModel model)
        {
            var user = model.GetUser<Caregiver>();

            // Call to asp net identity service layer
            var result = await _userManager.CreateAsync(user, model.Password);
            if (result.Succeeded)
            {
                result = await _userManager.AddToRoleAsync(user.Id, Role.CAREGIVER.ToString());
                if (result.Succeeded)
                {
                    user = _caregiverService.GetCaregiver(user.Id);
                    return Ok(user);
                }
            }

            return BadRequest("could not create the user or assign the role");
        }

        // PUT: api/Caregiver/5/Client/5
        [Route("{caregiverId}/client/{clientId}")]
        public void Put(string caregiverId, string clientId)
        {
            var succes = _caregiverService.AssignToCaregiver(caregiverId, clientId);
            if (succes)
            {
                Ok();
            }
            BadRequest($"caregiver with id {caregiverId} or client with id {clientId} could not be found or has already been assigned");
        }


    }
}
