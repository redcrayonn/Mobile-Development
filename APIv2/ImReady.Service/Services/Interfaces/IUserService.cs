using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReadyApiv2.Services.Interfaces
{
    public interface IUserService
    {
        List<User> GetUsers();
        User GetUser(string id);
        bool AddUser(User user);
        bool EditUser(User user);
        bool RemoveUser(string UserId);
    }
}
