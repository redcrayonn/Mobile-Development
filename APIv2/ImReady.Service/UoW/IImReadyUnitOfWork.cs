using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using Patterns.Repository;
using Patterns.UnitOfWork;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service
{
    public interface IImReadyUnitOfWork : IUnitOfWork
    {
        IRepository<Client> ClientRepository { get; }
        IRepository<User> UserRepository { get; }
        IRepository<Caregiver> CaregiverRepository { get; }
        IRepository<ClientBuildingBlock> ClientBuildingblockRepository { get; }
        IRepository<ClientActivity> ClientActivityRepository { get; }
        IRepository<Buildingblock> BuildingblockRepository { get; }
        IRepository<ClientComponent> ClientComponentRepository { get; }
        IRepository<Component> ComponentRepository { get; }
        IRepository<Notification> NotificationRepository { get; }
		IRepository<Calendar> CalendarRepository { get; }
		IRepository<ClientTask> ClientTaskRepository { get; }
	}
		
}
