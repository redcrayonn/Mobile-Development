using ImReady.Data.Enums;
using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results
{
    public class CaregiverClientsResult : BaseResult
    {
        public CaregiverClientsResult(Client client) : base(client)
        {
            Name = client.FirstName;
            NotificationCount = 0;
            foreach (var block in client.Blocks)
            {
                foreach (var components in block.Components)
                {
                    foreach (var activity in components.Activities)
                    {
                        if (activity.Status == Status.PENDING)
                        {
                            NotificationCount++;
                        }
                    }
                }
            }
        }
        public string Name { get; set; }
        public int NotificationCount { get; set; }
    }
}