using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input
{
    public class PostUserInputModel
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }

        internal T GetUser<T>() where T : User, new()
        {
            var user = new T
            {
                UserName = Email,
                Email = Email,
                FirstName = FirstName,
                LastName = LastName
            };
            return user;
        }
    }
}