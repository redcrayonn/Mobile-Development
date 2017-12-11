using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ImReady.Data.Models.Users;
using ImReadyApiv2.Context;
using ImReadyApiv2.Models;

namespace ImReadyApiv2.Services
{
    public class UserService : IUserService
    {
        private ImReadyDbContext _dbContext;

        public UserService()
        {
            _dbContext = new ImReadyDbContext();
        }


        public bool AddUser(User user)
        {
            try
            {
                _dbContext.Users.Add(user as ApplicationUser);
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }

        public bool EditUser(User user)
        {
            throw new NotImplementedException();
        }

        public User GetUser(string id)
        {
            try
            {
                var user = _dbContext.Users.SingleOrDefault(s => s.Id == id);
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
                return _dbContext.Users.Select(u => u as User).ToList();
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
                var user = _dbContext.Users.SingleOrDefault(s => s.Id == userId);
                _dbContext.Users.Remove(user);
            }
            catch (Exception)
            {
                return false;
            }
            return true;
        }
    }
}