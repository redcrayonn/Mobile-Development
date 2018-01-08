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
        public string access_token { get; set; }
        public string user_type { get; set; }
        public string expires_in { get; set; }
        public string firstname { get; set; }

        public bool IsValid()
        {
            return !string.IsNullOrWhiteSpace(UserName) && !string.IsNullOrWhiteSpace(access_token);
        }
    }
}
