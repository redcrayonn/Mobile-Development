using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models.Results
{
    public class LoginResult
    {
        public string UserName { get; set; }
        public string AuthToken { get; set; }

        public bool IsValid()
        {
            return !string.IsNullOrWhiteSpace(UserName) && !string.IsNullOrWhiteSpace(AuthToken);
        }
    }
}
