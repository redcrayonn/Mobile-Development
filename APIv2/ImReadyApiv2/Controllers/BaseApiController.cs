using ImReady.Data.Models.Users;
using ImReadyApiv2.Context;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace ImReadyApiv2.Controllers
{
    public abstract class BaseApiController : ApiController
    {
        private ApplicationUserManager _um;
        protected ApplicationUserManager _userManager
        {
            get
            {
                if (_um == null)
                {
                    var context = Request.GetOwinContext().Get<ImReadyDbContext>();
                    var store = new UserStore<User>(context);
                    _um = new ApplicationUserManager(store);
                }
                return _um;
            }
        }
    }
}