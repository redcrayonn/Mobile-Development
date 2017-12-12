using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models.Users;
using ImReadyApiv2.Services.Interfaces;
using ImReady.Service;
using Patterns.Repository;

namespace ImReadyApiv2.Services
{
    public class UserService : IUserService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;

        private readonly IRepository<User> _usersRepository;

        public UserService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            // save property reference to reduce code duplication. 
            // Could also be used as just unitOfWork.UserRepisotory.MethodName() instead...
            _usersRepository = unitOfWork.UserRepository;
        }


        public bool AddUser(User user)
        {
            try
            {
                _usersRepository.Add(user);
                // MUST BE CALLED after all entity changes/additions/removals. Otherwise data does NOT persist between sessions.
                // Can also use CommitAsync() if you want to have the save action run asynchronously
                _unitOfWork.Commit();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool EditUser(User user)
        {
            throw new NotImplementedException();
        }

        public User GetUser(string id)
        {
            try
            {
                var user = _usersRepository.Entities.SingleOrDefault(s => s.Id == id);
                return user;
            }
            catch (Exception)
            {
                return null;
            }
        }

        public List<User> GetUsers()
        {
            try
            {
                return _usersRepository.Entities.ToList();
            }
            catch (Exception)
            {
                return null;
            }
        }

        public bool RemoveUser(string userId)
        {
            try
            {
                var user = _usersRepository.Entities.SingleOrDefault(s => s.Id == userId);
                _usersRepository.Remove(user);
                _unitOfWork.Commit();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }
    }
}