using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models.Users;
using Patterns.Repository;
using Patterns.UnitOfWork;
using ImReady.Data.Models;

namespace ImReady.Service
{
    public class ImReadyUnitOfWork : EntityFrameworkUnitOfWork, IImReadyUnitOfWork
    {
        public ImReadyUnitOfWork(DbContext context) : base(context)
        {
            
        }

        //Users
        public IRepository<Client> ClientRepository => new EntityFrameworkGenericRepository<Client>(_context);
        public IRepository<User> UserRepository => new EntityFrameworkGenericRepository<User>(_context);
        public IRepository<Caregiver> CaregiverRepository => new EntityFrameworkGenericRepository<Caregiver>(_context);

        //Futureplan
        public IRepository<ClientBuildingBlock> ClientBuildingblockRepository => new EntityFrameworkGenericRepository<ClientBuildingBlock>(_context);
        public IRepository<Buildingblock> BuildingblockRepository => new EntityFrameworkGenericRepository<Buildingblock>(_context);
        public IRepository<ClientActivity> ClientActivityRepository => new EntityFrameworkGenericRepository<ClientActivity>(_context);
        public IRepository<ClientComponent> ClientComponentRepository => new EntityFrameworkGenericRepository<ClientComponent>(_context);
        public IRepository<Component> ComponentRepository => new EntityFrameworkGenericRepository<Component>(_context);

        public IRepository<Notification> NotificationRepository => new EntityFrameworkGenericRepository<Notification>(_context);
    }
}
