using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReady.Service.Services.Interfaces;
using ImReadyApiv2.Services.Interfaces;
using System.Collections.Generic;
using System.Web.Http;
using System.Web.Http.Description;

namespace ImReadyApiv2.Controllers {

	[RoutePrefix("api/user/{userId}/calendar")]
	public class CalendarController : BaseApiController{

		private readonly IUserService _userService;
		private readonly ICalendarService _calendarService;

		public CalendarController (IUserService userService, ICalendarService calendarService) {
			_userService = userService;
			_calendarService = calendarService;
		}

		[ResponseType(typeof(List<Calendar>))]
		[Route("")]
		public IHttpActionResult Get(string userId) {
			User user = _userService.GetUser(userId);

			if (user == null) {
				return NotFound();
			}

			List<Calendar> calendarItems = _calendarService.Get(userId);
			
			return Ok(calendarItems);
		}
	}
}