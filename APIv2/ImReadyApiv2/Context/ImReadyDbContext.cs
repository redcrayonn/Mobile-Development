using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using ImReadyApiv2.Models;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Data.Entity;

namespace ImReadyApiv2.Context
{
    public class ImReadyDbContext : IdentityDbContext<ApplicationUser>
    {
        public ImReadyDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {
        }
        
        public static ImReadyDbContext Create()
        {
            return new ImReadyDbContext();
        }

        // Users
        public virtual DbSet<Client> Clients { get; set; }
        public virtual DbSet<Caregiver> Caregivers { get; set; }
        public virtual DbSet<Relative> Relatives { get; set; }

        // Block data (general)
        public virtual DbSet<Buildingblock> Buildingblocks { get; set; }
        public virtual DbSet<Component> Components { get; set; }
        public virtual DbSet<Activity> Activities { get; set; }

        // Block data (personal)
        public virtual DbSet<ClientBuildingBlock> ClientBuildingblocks { get; set; }
        public virtual DbSet<ClientComponent> ClientComponents { get; set; }
        public virtual DbSet<ClientActivity> ClientActivities { get; set; }

        // Chats
        //public virtual DbSet<Chat> Chats { get; set; }
        //public virtual DbSet<Message> Messages { get; set; }
    }
}