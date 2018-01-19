using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class CurrentUser
    {
        private static CurrentUser user { get; set; }
        public static CurrentUser SingleInstance
        {
            get
            {
                if (user == null)
                    user = new CurrentUser();
                return user;
            }
            set
            {
                user = value;
            }
        }

        private CurrentUser()
        {

        }

        public string AccessToken { get; set; }
        public string Username { get; set; }
        public string FirstName { get; set; }
        public string Id { get; set; }
        public bool IsLoggedIn => !string.IsNullOrEmpty(AccessToken);
    }
}
