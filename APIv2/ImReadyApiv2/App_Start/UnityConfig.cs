using ImReady.Service;
using ImReady.Service.Services;
using ImReady.Service.Services.Interfaces;
using ImReadyApiv2.Context;
using ImReadyApiv2.Services;
using ImReadyApiv2.Services.Interfaces;
using System.Data.Entity;
using System.Web.Http;
using Unity;
using Unity.Lifetime;

namespace ImReadyApiv2.DI
{
    class UnityConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Setup container
            var container = new UnityContainer();

            // Setup DbContext & UoW
            container.RegisterType<DbContext, ImReadyDbContext>(new HierarchicalLifetimeManager());
            container.RegisterType<IImReadyUnitOfWork, ImReadyUnitOfWork>(new HierarchicalLifetimeManager());

            // Register services
            container.RegisterType<IUserService, UserService>(new HierarchicalLifetimeManager());
            container.RegisterType<IClientService, ClientService>(new HierarchicalLifetimeManager());
            container.RegisterType<IFutureplanService, FutureplanService>(new HierarchicalLifetimeManager());
            container.RegisterType<IBuildingblockService, BuildingblockService>(new HierarchicalLifetimeManager());
            container.RegisterType<IClientActivityService, ClientActivityService>(new HierarchicalLifetimeManager());

            // Set dependency resolver to the Unity resolver
            var resolver = new UnityResolver(container);
            config.DependencyResolver = resolver;
            GlobalConfiguration.Configuration.DependencyResolver = resolver;
        }
    }
}
