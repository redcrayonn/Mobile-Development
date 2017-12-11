using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models.Users;
using Patterns.Repository;
using Patterns.UnitOfWork;

namespace ImReady.Service
{
    public class ImReadyUnitOfWork : EntityFrameworkUnitOfWork, IImReadyUnitOfWork
    {
        public ImReadyUnitOfWork(DbContext context) : base(context)
        {
            
        }

        public IRepository<Client> ClientRepository => new EntityFrameworkGenericRepository<Client>(_context);
        public IRepository<User> UserRepository => new EntityFrameworkGenericRepository<User>(_context);
    }
}
