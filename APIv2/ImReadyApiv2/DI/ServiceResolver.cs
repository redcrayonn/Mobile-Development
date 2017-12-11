using ImReadyApiv2.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Dependencies;
using Unity;
using Unity.Lifetime;

namespace ImReadyApiv2.DI
{
    class ServiceResolver
    {
        public static void Register(HttpConfiguration config)
        {
            var container = new UnityContainer();
            //container.RegisterType<IClientService, ClientService>(new HierarchicalLifetimeManager());
            container.RegisterType<IUserService, UserService>(new HierarchicalLifetimeManager());

            config.DependencyResolver = new UnityResolver(container);
        }
    }
}
