using ImReady.Service.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models.Users;
using Patterns.Repository;
using ImReady.Data.Models;
using ImReady.Data.Enums;

namespace ImReady.Service.Services
{
    public class CaregiverService : ICaregiverService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<Caregiver> _caregiverRepository;
        private readonly IRepository<Client> _clientRepository;
        private readonly IRepository<ClientActivity> _clientActivityRepository;

        public CaregiverService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _caregiverRepository = _unitOfWork.CaregiverRepository;
            _clientRepository = _unitOfWork.ClientRepository;
            _clientActivityRepository = _unitOfWork.ClientActivityRepository;
        }

        public Caregiver GetCaregiver(string id)
        {
            var client = _caregiverRepository.Entities.FirstOrDefault(c => c.Id == id);
            return client;
        }

        public bool AssignToCaregiver(string caregiverId, string clientId)
        {
            if (!UsersExist(caregiverId, clientId, out var caregiver, out var client))
            {
                // user entities could not be found
                return false;
            }

            if (caregiver.Clients.Any(c => c.Id == client.Id))
            {
                // client has already been added
                return false;
            }

            caregiver.Clients.Add(client);
            _unitOfWork.Commit();

            return true;
        }

        public bool ApproveActivity(string caregiverId, string clientId, string activityId, string caregiverFeedback)
        {
            if (!TryPrepareFeedback(caregiverId, clientId, activityId, caregiverFeedback, out var activity))
            {
                // entities were not found or something went wrong with the feedback creation
                return false;
            }

            if (activity.Status != Status.PENDING)
            {
                // entity is not in the pending state
                return false;
            }

            activity.Status = Status.DONE;
            // set the component status to done if all it's activities are done as well
            if (activity.ClientComponent.Activities.All(a => a.Status == Status.DONE))
            {
                activity.ClientComponent.Status = Status.DONE;
            }

            _unitOfWork.Commit();
            return true;
        }

        public bool DeclineActivity(string caregiverId, string clientId, string activityId, string caregiverFeedback)
        {
            if (!TryPrepareFeedback(caregiverId, clientId, activityId, caregiverFeedback, out var activity))
            {
                // entities were not found or something went wrong with the feedback creation
                return false;
            }
            
            if (activity.Status != Status.PENDING)
            {
                // entity is not in the pending state
                return false;
            }

            activity.Status = Status.ONGOING;

            _unitOfWork.Commit();
            return true;
        }

        private bool TryPrepareFeedback(string caregiverId, string clientId, string activityId, string caregiverFeedback, out ClientActivity activity)
        {
            if (!UsersExist(caregiverId, clientId, out var caregiver, out var client))
            {
                // user entities could not be found
                activity = null;
                return false;
            }

            activity = _clientActivityRepository.Entities.FirstOrDefault(a => a.Id == activityId);
            if (activity == null)
            {
                // activity could not be found
                return false;
            }

            var feedback = new Feedback
            {
                Caregiver = caregiver,
                ClientActivity = activity,
                Content = caregiverFeedback
            };
            activity.Feedback.Add(feedback);

            return true;
        }

        private bool UsersExist(string caregiverId, string clientId, out Caregiver caregiver, out Client client)
        {
            caregiver = _caregiverRepository.Entities.FirstOrDefault(c => c.Id == caregiverId);
            client = _clientRepository.Entities.FirstOrDefault(c => c.Id == clientId);

            if (caregiver == null || client == null)
            {
                // either the caregiver or client could not be found
                return false;
            }
            return true;
        }
    }
}
