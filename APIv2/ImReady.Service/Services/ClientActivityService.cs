using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Enums;
using ImReady.Data.Models;
using Patterns.Repository;

namespace ImReady.Service.Services
{
    public class ClientActivityService : IClientActivityService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<ClientActivity> _clientActivityRepository;

        public ClientActivityService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _clientActivityRepository = _unitOfWork.ClientActivityRepository;
        }

        public bool AddActivity(ClientActivity activity)
        {
            throw new NotImplementedException();
        }

        public void ChangeStatus(string Id, Status status)
        {
            ClientActivity activity = _clientActivityRepository.Entities.SingleOrDefault(s => s.Id == Id);
            activity.Status = status;
            _unitOfWork.Commit();
        }

        public bool EditActivity(ClientActivity activity)
        {
            ClientActivity dbActivity = _clientActivityRepository.Entities.SingleOrDefault(x => x.Id == activity.Id);

            dbActivity.Content = activity.Content;
            dbActivity.Deadline = activity.Deadline;
            dbActivity.Feedback = activity.Feedback;
            dbActivity.Status = activity.Status;
            try
            {
                _unitOfWork.Commit();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public ICollection<ClientActivity> getAll(string componentId)
        {
            return _clientActivityRepository.Entities.Where(x => !x.Deleted && x.ClientComponentId == componentId).ToList();
        }

        public ClientActivity getById(string Id)
        {
            return _clientActivityRepository.Entities.SingleOrDefault(s => s.Id == Id);
        }

        public bool RemoveActivity(string Id)
        {
            var activity = _clientActivityRepository.Entities.SingleOrDefault(s => s.Id == Id);

            if (activity != null)
            {
                try
                {
                    _clientActivityRepository.Remove(activity);
                    _unitOfWork.Commit();
                    return true;    

                }
                catch (Exception)
                {
                    return false;
                }
            }
            return false;
        }
    }
}
