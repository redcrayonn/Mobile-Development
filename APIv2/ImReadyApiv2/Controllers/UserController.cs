using ImReady.Data.Models.Users;
using ImReadyApiv2.Results;
using ImReadyApiv2.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using System.Web.Http.Results;

namespace ImReadyApiv2.Controllers
{
    /// <summary>
    /// Api controller for User calls
    /// </summary>
    public class UserController : BaseApiController
    {
        private readonly IUserService _userService;

        /// <summary>
        /// ctor
        /// </summary>
        /// <param name="userService">Service class for user business logic</param>
        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        /// <summary>
        /// Gets all users
        /// </summary>
        /// <remarks>Returns all users</remarks>
        /// <response code="200">OK</response>
        /// <response code="500">Internal Server Error</response>
        [ResponseType(typeof(List<User>))]
        public async Task<IHttpActionResult> GetAsync()
        {
            List<User> users = _userService.GetUsers();
            if (users != null)
            {
                var result = users.Select(u => new UserResult(u)).ToList();
                foreach (var r in result)
                {
                    var roles = await _userManager.GetRolesAsync(r.Id);
                    r.Roles = roles;
                }
                return Ok(result);
            }
            return InternalServerError();
        }

        /// <summary>
        /// Returns a user
        /// </summary>
        /// <param name="id">Hte user id</param>
        /// <response code="200">OK</response>
        /// <response code="404">Not Found</response>
        [ResponseType(typeof(User))]
        public IHttpActionResult Get(string id)
        {
            User user = _userService.GetUser(id);

            if (user != null)
            {
                return Ok(user);
            }
            else
            {
                return NotFound();
            }
        }
    }
}
