using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models.Users;

namespace ImReadyApiv2.Results
{
    public class UserResult : BaseResult
    {
        public string FirstName { get; }
        public string LastName { get; }

        public UserResult(Client client) : base(client)
        {
            FirstName = client.FirstName;
            LastName = client.LastName;
        }
    }
}