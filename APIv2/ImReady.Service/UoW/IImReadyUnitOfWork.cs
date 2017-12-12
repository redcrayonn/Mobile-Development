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
        IRepository<ClientBuildingBlock> ClientBuildingblockRepository { get; }
        IRepository<Buildingblock> BuildingblockRepository { get; }
    }
}
