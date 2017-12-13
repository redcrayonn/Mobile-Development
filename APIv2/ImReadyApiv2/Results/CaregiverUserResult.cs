using System.Collections.Generic;
using ImReady.Data.Models.Users;
using System.Linq;

namespace ImReadyApiv2.Results
{
    public class CaregiverUserResult : UserResult
    {
        public CaregiverUserResult(Caregiver caregiver, bool fullModel = false) : base(caregiver)
        {
            if (fullModel)
            {
                Clients = caregiver.Clients.Select(x => new ClientUserResult(x));
            }
        }

        public IEnumerable<ClientUserResult> Clients { get; }
    }
}