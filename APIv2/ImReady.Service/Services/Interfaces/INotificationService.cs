using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services.Interfaces
{
    public interface INotificationService
    {
        bool CreateNotification(NotificationType notificationType, string senderId, string receiverId, EntityModel model);
    }
}
