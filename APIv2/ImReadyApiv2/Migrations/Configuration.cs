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
    using ImReady.Data.Enums;
    using System.Collections.Generic;

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
            await SeedUsers(context);
            await SeedData(context);
        }

        private async System.Threading.Tasks.Task SeedUsers(ImReadyDbContext context)
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

            var client = new Client()
            {
                UserName = "Jantje@client.nl",
                FirstName = "Jantje",
                LastName = "Bakker",
                Email = "Jantje@client.nl",
                EmailConfirmed = true,
                Points = 0,
            };

            var result2 = await userManager.CreateAsync(client, "test123");
            if (result2.Succeeded)
            {
                result2 = await userManager.AddToRoleAsync(client.Id, Role.CLIENT.ToString());
            }
        }

        private async System.Threading.Tasks.Task SeedData(ImReadyDbContext context)
        {
            #region WORK BLOCK
            var workBlock = new Buildingblock
            {
                Name = "Werk",
                Description = "Alles om werk te regelen",
                Id = BlockType.WORK.ToString(),
                Type = BlockType.WORK
            };
            #endregion

            #region STUDY BLOCK
            var studyBlock = new Buildingblock
            {
                Name = "Verzekering",
                Description = "Alles om je studie te regelen",
                Id = BlockType.EDUCATION.ToString(),
                Type = BlockType.EDUCATION
            };
            #endregion

            #region LIVING BLOCK
            var rent = new Component
            {
                Id = BlockType.LIVING.ToString() + "_rent",
                Description = "Bij een huis huren komt heel wat kijken...",
                Name = "Huis huren"
            };

            var insurance = new Component
            {
                Id = BlockType.LIVING.ToString() + "_insurance",
                Description = "Heel wat spullen zijn wat waard in je huis. Handig om dit te verzekeren.",
                Name = "Inboedelverzekering regelen"
            };

            var livingBlockComponents = new List<Component>()
            {
                rent,
                insurance
            };

            var livingBlock = new Buildingblock
            {
                Name = "Wonen",
                Description = "Alles om wonen te regelen",
                Id = BlockType.LIVING.ToString(),
                Type = BlockType.LIVING,
                Components = livingBlockComponents
            };
            #endregion

            #region FINANCE BLOCK
            var financeBlock = new Buildingblock
            {
                Name = "Financien",
                Description = "Alles om geldzaken te regelen",
                Id = BlockType.MONEY.ToString(),
                Type = BlockType.MONEY
            };
            #endregion

            #region INSURANCE BLOCK
            var insuranceBlock = new Buildingblock
            {
                Name = "Verzekering",
                Description = "Alles om verzekeringen te regelen",
                Id = BlockType.INSURANCE.ToString(),
                Type = BlockType.INSURANCE
            };
            #endregion

            #region TREATMENT BLOCK
            var treatmentBlock = new Buildingblock
            {
                Name = "Behandelplan",
                Description = "Alles om je behandelplan te regelen",
                Id = BlockType.TREATMENTPLAN.ToString(),
                Type = BlockType.TREATMENTPLAN
            };
            #endregion

            #region SOCIAL BLOCK
            var socialBlock = new Buildingblock
            {
                Name = "Sociaal",
                Description = "Alles om sociale zaken te regelen",
                Id = BlockType.SOCIAL.ToString(),
                Type = BlockType.SOCIAL
            };
            #endregion

            #region HEALTH BLOCK
            var healthBlock = new Buildingblock
            {
                Name = "Gezondheid",
                Description = "Alles om je gezondheid te regelen",
                Id = BlockType.HEALTH.ToString(),
                Type = BlockType.HEALTH
            };
            #endregion

            #region FAMILY BLOCK
            var familyBlock = new Buildingblock
            {
                Name = "Gezin",
                Description = "Alles om gezinszaken te regelen",
                Id = BlockType.FAMILY.ToString(),
                Type = BlockType.FAMILY
            };
            #endregion

            #region RIGHTS BLOCK
            var rightsBlock = new Buildingblock
            {
                Name = "Rechten & Plichten",
                Description = "Alles om verzekeringen te regelen",
                Id = BlockType.RIGHTSANDOBLIGATIONS.ToString(),
                Type = BlockType.RIGHTSANDOBLIGATIONS
            };
            #endregion


            context.Buildingblocks.AddOrUpdate(workBlock);
            context.Buildingblocks.AddOrUpdate(studyBlock);
            context.Buildingblocks.AddOrUpdate(livingBlock);
            context.Buildingblocks.AddOrUpdate(financeBlock);
            context.Buildingblocks.AddOrUpdate(insuranceBlock);
            context.Buildingblocks.AddOrUpdate(treatmentBlock);
            context.Buildingblocks.AddOrUpdate(socialBlock);
            context.Buildingblocks.AddOrUpdate(healthBlock);
            context.Buildingblocks.AddOrUpdate(familyBlock);
            context.Buildingblocks.AddOrUpdate(rightsBlock);

            await context.SaveChangesAsync();
        }
    }
}
