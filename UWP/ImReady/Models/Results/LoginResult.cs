using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models.Results
{
    public class LoginResult
    {
        //Custom props
        public string UserName { get; set; }

        //JSON props
        public string access_token { get; set; }
        public string token_type { get; set; }
        public string user_type { get; set; }
        public double expires_in { get; set; }
        public string firstname { get; set; }
        public string user_id { get; set; }
        public string issued { get; set; }
        public string expires { get; set; }

        public bool IsValid()
        {
            return !string.IsNullOrWhiteSpace(UserName) && !string.IsNullOrWhiteSpace(access_token);
        }
    }
}
