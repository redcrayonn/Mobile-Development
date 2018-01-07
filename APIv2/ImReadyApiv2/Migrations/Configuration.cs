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
            var workBlock = new Buildingblock()
            {
                Name = "Werk",
                Description = "De bouwsteen 'Werk' helpt jou met het vinden van een baan. Een baan levert jou geld op, zodat je financieel onafhankelijk wordt. Het begint met jezelf leren kennen. Wat wil je? Waar ben je goed in? Daarna zoek je een bedrijf en ga je daar solliciteren.",
                Id = BlockType.WORK.ToString(),
                Components = new List<Component>
                {
                    new Component
                    {
                        Id = BlockType.WORK+"_orientation",
                        Name = "Oriënteren",
                        Description = "Voordat je een baan gaat zoeken moet je eerst weten wat bij jou past.",
                        Activities = new List<Activity>
                        {
                            new Activity
                            {
                                Id = BlockType.WORK+"_orientation_strengths",
                                Name = "Waar ben je goed in?",
                                Points = 3
                            },
                            new Activity
                            {
                                Id = BlockType.WORK+"_orientation_interests",
                                Name = "Wat vindt je leuk?",
                                Points = 3
                            },
                            new Activity
                            {
                                Id = BlockType.WORK+"_orientation_search",
                                Name = "Zoek een bedrijf uit",
                                Points = 4
                            }
                        }
                    },
                    new Component
                    {
                        Id = BlockType.WORK+"_solicitate",
                        Name = "Solliciteren",
                        Description = "Solliciteren is het verkopen van jezelf aan het bedrijf. Laat je sterke punten zien die bij het onderdeel oriënteren naar voren kwamen. Je zal de contactpersoon jouw verwachtingen van de baan moeten uitleggen en je verdiepen in de situatie van het bedrijf. Je zal een CV opstellen en hiermee informeer je het bedrijf over jezelf. Ook ga je een sollicitatiebrief sturen, hiermee zorg je dat het bedrijf interrese krijgt. Daarna kun je uitgenodigd worden voor een gesprek."
                        + "Zorg er voor dat je echt op tijd bent, zowel met het inleveren van stukken als het aanwezig zijn bij het bedrijf.",
                        YoutubeURL = "https://youtu.be/Wj6MuHAbSjU",
                        UsefulLinks = new List<UsefulLink>
                        {
                            new UsefulLink
                            {
                                Id = "CV opstellen? Drie onmisbare tips!",
                                Url = "$YT_Wj6MuHAbSjU"
                            },
                            new UsefulLink
                            {
                                Id = "CV opstellen tips",
                                Url = "http://www.carrieretijger.nl/carriere/solliciteren/sollicitatiebrief-en-cv/cv-opstellen"
                            }
                        },
                        Activities = new List<Activity>
                        {
                            new Activity
                            {
                                Id = BlockType.WORK+"_solicitate_cv",
                                Name = "CV opstellen",
                                Description = "Het opstellen van een Curriculum Vitae (CV).\r\n\r\nWat moet er in mijn CV:\r\n    -Representatieve foto van jezelf\r\n    -NAW-gegevens\r\n    -Vaardigheden\r\n    -Werkervaring",
                                Points = 5
                            }
                        }
                    }
                }
            };
            #endregion

            #region STUDY BLOCK
            var studyBlock = new Buildingblock
            {
                Name = "Studie",
                Description = "Alles om je studie te regelen",
                Id = BlockType.EDUCATION.ToString(),
                Type = BlockType.EDUCATION
            };
            #endregion

            #region LIVING BLOCK
            var livingBlock = new Buildingblock
            {
                Name = "Wonen",
                Description = "Alles om wonen te regelen",
                Id = BlockType.LIVING.ToString(),
                Type = BlockType.LIVING,
                Components = new List<Component>()
                {
                    new Component
                    {
                        Id = BlockType.LIVING.ToString() + "_rent",
                        Description = "Bij een huis huren komt heel wat kijken...",
                        Name = "Huis huren",
                        Activities = new List<Activity>()
                        {
                            new Activity
                            {
                                Id = $"{BlockType.LIVING.ToString()}_rent_enrollcity",
                                Description = "Inschrijven bij de gemeente kan ervoor zorgen dat je een sociale huurwoning kan ontvangen. Al moet je daar nog wel meer voor doen.",
                                Name = "Schrijf je in bij de gemeente",
                                Points = 2
                            },
                            new Activity
                            {
                                Id = $"{BlockType.LIVING.ToString()}_rent_findappartments",
                                Description = "Verspreid je kansen!",
                                Name = "Reageer op 5 woningen",
                                Points = 10
                            }
                        }
                    },
                    new Component
                    {
                        Id = BlockType.LIVING.ToString() + "_insurance",
                        Description = "Heel wat spullen zijn wat waard in je huis. Handig om dit te verzekeren.",
                        Name = "Inboedelverzekering regelen"
                    }
                }
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
                Description = "Alles om je rechten & plichten te regelen",
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
