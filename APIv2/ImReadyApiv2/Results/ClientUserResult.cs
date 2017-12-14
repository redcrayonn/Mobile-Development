using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models.Users;

namespace ImReadyApiv2.Results
{
    public class ClientUserResult : UserResult
    {
        public int Points { get; }
        public CaregiverUserResult Caregiver { get; }

        public ClientUserResult(Client client, bool fullModel = false) : base(client)
        {
            Points = client.Points;
            
            if (fullModel)
            {
                if (client.Caregiver != null)
                {
                    Caregiver = new CaregiverUserResult(client.Caregiver, false);
                }
            }
            
        }
    }
}