﻿using ImReady.Data;
using ImReady.Service;
using ImReady.Service.Services;
using ImReadyApiv2.Context;
using ImReadyApiv2.Controllers;
using ImReadyApiv2.Services;
using Patterns.UnitOfWork;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Dependencies;
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

            // Set dependency resolver to the Unity resolver
            var resolver = new UnityResolver(container);
            config.DependencyResolver = resolver;
            GlobalConfiguration.Configuration.DependencyResolver = resolver;
        }
    }
}
