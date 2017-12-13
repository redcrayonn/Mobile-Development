using ImReady.Data.Enums;
using ImReady.Data.Models;
using ImReady.Service.Services.Interfaces;
using Patterns.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public class NotificationService : INotificationService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<Notification> _notificationRepository;

        public NotificationService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _notificationRepository = _unitOfWork.NotificationRepository;
        }

        public bool CreateNotification(NotificationType type, string senderId, string receiverId, EntityModel model)
        {
            if (model == null)
            {
                return false;
            }

            // TODO: check model type to make sure it's allowed

            var notification = new Notification
            {
                CreatedDate = DateTime.Now,
                IsRead = false,
                ObjectId = model.Id,
                ReceiverId = receiverId,
                SenderId = senderId,
                Type = type
            };

            _notificationRepository.Add(notification);
            _unitOfWork.Commit();
            return true;
        }
    }
}
