using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models.Users;

namespace ImReadyApiv2.Results
{
    public class UserResult : BaseResult
    {
        public string Email { get; }
        public string FirstName { get; }
        public string LastName { get; }
        public IEnumerable<string> Roles { get; set; }

        public UserResult(User user) : base(user)
        {
            FirstName = user.FirstName;
            LastName = user.LastName;
            Email = user.Email;
        }
    }
}