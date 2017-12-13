using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class User
    {
        private static User user { get; set; }
        public static User SingleInstance
        {
            get
            {
                if (user == null)
                    user = new User();
                return user;
            }
            set
            {
                user = value;
            }
        }

        private User()
        {

        }

        public string AuthToken { get; set; }
        public string Username { get; set; }

        public bool IsLoggedIn => !string.IsNullOrEmpty(AuthToken);
    }
}
