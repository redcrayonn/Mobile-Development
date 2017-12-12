namespace ImReadyApiv2.Migrations
{
    using ImReady.Data.Models;
    using ImReady.Data.Models.Users;
    using ImReadyApiv2.Models;
    using Microsoft.AspNet.Identity.EntityFramework;
    using System;
    using System.Data.Entity.Migrations;
    using System.Linq;
    using System.Threading.Tasks;
    using ImReadyApiv2.Context;

    internal sealed class Configuration : DbMigrationsConfiguration<ImReadyApiv2.Context.ImReadyDbContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = true;
            AutomaticMigrationDataLossAllowed = true;
        }

        protected override void Seed(ImReadyApiv2.Context.ImReadyDbContext context)
        {
            //  This method will be called after migrating to the latest version.

            //  You can use the DbSet<T>.AddOrUpdate() helper extension method 
            //  to avoid creating duplicate seed data.

            System.Threading.Tasks.Task.Run(async () => await SeedDatabase(context)).Wait();
        }

        private async System.Threading.Tasks.Task SeedDatabase(ImReadyApiv2.Context.ImReadyDbContext context)
        {
            var roles = Enum.GetNames(typeof(Role));

            var roleStore = new RoleStore<IdentityRole>(context);
            foreach (string role in roles)
            {
                if (!context.Roles.Any(r => r.Name == role))
                {
                    await roleStore.CreateAsync(new IdentityRole(role));
                }
            }

            var admin = new User()
            {
                UserName = "ADMIN",
                FirstName = "Admin",
                LastName = "Adminius",
                Email = "admin@imready.nl",
                EmailConfirmed = true
            };

            var userStore = new UserStore<User>(context);
            var userManager = new ApplicationUserManager(userStore);
            var result = await userManager.CreateAsync(admin, "admin123");
            if (result.Succeeded)
            {
                result = await userManager.AddToRoleAsync(admin.Id, Role.ADMIN.ToString());
            }

            SeedBuildingBlocks(context);
            SeedComponents(context);
        }

        private void SeedComponents(ImReadyDbContext context)
        {
            context.Components.AddOrUpdate(new Component { BuildingblockId = "1", Deleted = false, Description = "Bij een huis huren komt heel wat kijken...", Name = "Huis huren", Id = "1" });
            context.Components.AddOrUpdate(new Component { BuildingblockId = "2", Deleted = false, Description = "Heel wat spullen zijn wat waard in je huis. Handig om dit te verzekeren.", Name = "Inboedelverzekering regelen", Id = "2" });
            context.SaveChanges();
        }

        private void SeedBuildingBlocks(ImReadyDbContext context)
        {
            context.Buildingblocks.AddOrUpdate(new Buildingblock { Name = "Wonen", Deleted = false, Description = "Alles om wonen te regelen", Id = "1", Type = ImReady.Data.Enums.BlockType.LIVING });
            context.Buildingblocks.AddOrUpdate(new Buildingblock { Name = "Verzekering", Deleted = false, Description = "Alles om verzekeringen te regelen", Id = "2", Type = ImReady.Data.Enums.BlockType.INSURANCE });
            context.SaveChanges();
        }
    }
}
