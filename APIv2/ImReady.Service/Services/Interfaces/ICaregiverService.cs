using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models.Users;

namespace ImReady.Service.Services
{
    public interface ICaregiverService
    {
        Caregiver GetCaregiver(string id);
        bool AssignToCaregiver(string caregiverId, string clientId);
        bool ApproveActivity(string caregiverId, string clientId, string activityId, string caregiverFeedback);
        bool DeclineActivity(string caregiverId, string clientId, string activityId, string caregiverFeedback);
    }
}
