using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models.Users
{
    public abstract class User : EntityModel
    {
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        public string Password { get; set; }
        public string Phonenumber { get; set; }
        public string Email { get; set; }
        public Role Role { get; set; }
    }
}
