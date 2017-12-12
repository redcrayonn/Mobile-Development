using System.Collections.Generic;
using ImReady.Data.Models.Users;

namespace ImReadyApiv2.Results
{
    public class CaregiverUserResult : UserResult
    {
        public CaregiverUserResult(Caregiver caregiver, bool fullModel = false) : base(caregiver)
        {
            if (fullModel)
            {
                Clients = caregiver.Clients;
            }
        }

        public ICollection<Client> Clients { get; }
    }
}