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
using System.Web.Http.Description;

namespace ImReadyApiv2.Controllers
{
    /// <summary>
    /// Api controller for caregiver actions
    /// </summary>
    [RoutePrefix("api/caregiver")]
    public class CaregiverController : BaseApiController
    {
        private readonly ICaregiverService _caregiverService;

        /// <summary>
        /// ctor
        /// </summary>
        /// <param name="userService">Service class for caregiver business logic</param>
        public CaregiverController(ICaregiverService userService)
        {
            _caregiverService = userService;
        }

        /// <summary>
        /// Gets the information of a caregiver
        /// </summary>
        /// <param name="id">Id of the caregiver</param>
        /// <remarks>Gets the information of a caregiver</remarks>
        /// <response code="200">OK</response>
        /// <response code="404">Not Found</response>
        [Route("{id}")]
        [ResponseType(typeof(CaregiverUserResult))]
        public async Task<IHttpActionResult> Get(string id)
        {
            var caregiver = _caregiverService.GetCaregiver(id);
            if (caregiver == null)
            {
                return NotFound();
            }
            var caregiverResult = new CaregiverUserResult(caregiver, true)
            {
                Roles = await _userManager.GetRolesAsync(caregiver.Id)
            };
            return Ok(caregiverResult);
        }

        /// <summary>
        /// Creates a new caregiver
        /// </summary>
        /// <param name="model">The input model</param>
        /// <remarks>Inserts a new Caregiver</remarks>
        /// <response code="200">OK</response>
        /// <response code="400">Bad Request</response>
        [Route("")]
        [ResponseType(typeof(Caregiver))]
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

        /// <summary>
        /// Assigns a client to a caregiver
        /// </summary>
        /// <param name="caregiverId">The caregiver id</param>
        /// <param name="clientId">The client id</param>
        /// <remarks>Add Client to Caregiver</remarks>
        /// <response code="200">OK</response>
        /// <response code="400">Bad Request</response>
        [Route("{caregiverId}/client/{clientId}")]
        public IHttpActionResult Put(string caregiverId, string clientId)
        {
            var succes = _caregiverService.AssignToCaregiver(caregiverId, clientId);
            if (succes)
            {
                return Ok();
            }
            return BadRequest($"caregiver with id {caregiverId} or client with id {clientId} could not be found or has already been assigned");
        }

        /// <summary>
        /// Approves or decline a client's handed in activity/assignment/task
        /// </summary>
        /// <param name="caregiverId"></param>
        /// <param name="clientId"></param>
        /// <param name="activityId"></param>
        /// <returns></returns>
        [Route("{caregiverId}/client/{clientId}/activity/{activityId}")]
        public IHttpActionResult Put(string caregiverId, string clientId, string activityId, [FromBody]PutCaregiverActivityInputModel model)
        {
            bool succes;
            if (model.Approved)
            {
                succes = _caregiverService.ApproveActivity(caregiverId, clientId, activityId, model.Feedback);
            }
            else
            {
                succes = _caregiverService.DeclineActivity(caregiverId, clientId, activityId, model.Feedback);
            }

            if (succes)
            {
                return Ok();
            }
            return BadRequest();
        }
    }
}
