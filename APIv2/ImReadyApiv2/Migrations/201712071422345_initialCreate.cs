namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initialCreate : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Activity",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Name = c.String(),
                        Description = c.String(),
                        Points = c.Int(nullable: false),
                        ComponentId = c.Guid(nullable: false),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Component", t => t.ComponentId, cascadeDelete: true)
                .Index(t => t.ComponentId);
            
            CreateTable(
                "dbo.Component",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        BuildingblockId = c.Guid(nullable: false),
                        Name = c.String(),
                        Description = c.String(),
                        YoutubeURL = c.String(),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Buildingblock", t => t.BuildingblockId, cascadeDelete: true)
                .Index(t => t.BuildingblockId);
            
            CreateTable(
                "dbo.Buildingblock",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Name = c.String(),
                        Description = c.String(),
                        Type = c.Int(nullable: false),
                        Deleted = c.Boolean(nullable: false),
                        Client_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Client", t => t.Client_Id)
                .Index(t => t.Client_Id);
            
            CreateTable(
                "dbo.UsefulLink",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        ComponentId = c.Guid(nullable: false),
                        Url = c.String(),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Component", t => t.ComponentId, cascadeDelete: true)
                .Index(t => t.ComponentId);
            
            CreateTable(
                "dbo.User",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Firstname = c.String(),
                        Lastname = c.String(),
                        Role = c.Int(nullable: false),
                        Email = c.String(),
                        EmailConfirmed = c.Boolean(nullable: false),
                        PasswordHash = c.String(),
                        SecurityStamp = c.String(),
                        PhoneNumber = c.String(),
                        PhoneNumberConfirmed = c.Boolean(nullable: false),
                        TwoFactorEnabled = c.Boolean(nullable: false),
                        LockoutEndDateUtc = c.DateTime(),
                        LockoutEnabled = c.Boolean(nullable: false),
                        AccessFailedCount = c.Int(nullable: false),
                        UserName = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.AspNetUserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.String(nullable: false, maxLength: 128),
                        ClaimType = c.String(),
                        ClaimValue = c.String(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.User", t => t.UserId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserLogins",
                c => new
                    {
                        LoginProvider = c.String(nullable: false, maxLength: 128),
                        ProviderKey = c.String(nullable: false, maxLength: 128),
                        UserId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.LoginProvider, t.ProviderKey, t.UserId })
                .ForeignKey("dbo.User", t => t.UserId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserRoles",
                c => new
                    {
                        UserId = c.String(nullable: false, maxLength: 128),
                        RoleId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.UserId, t.RoleId })
                .ForeignKey("dbo.User", t => t.UserId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetRoles", t => t.RoleId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);
            
            CreateTable(
                "dbo.ClientBuildingBlock",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        BuildingblockId = c.Guid(nullable: false),
                        ClientId = c.String(maxLength: 128),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Buildingblock", t => t.BuildingblockId, cascadeDelete: true)
                .ForeignKey("dbo.Client", t => t.ClientId)
                .Index(t => t.BuildingblockId)
                .Index(t => t.ClientId);
            
            CreateTable(
                "dbo.ClientComponent",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Description = c.String(),
                        ClientBuildingBlockId = c.Guid(nullable: false),
                        Status = c.Int(nullable: false),
                        TaskId = c.Guid(nullable: false),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.ClientBuildingBlock", t => t.ClientBuildingBlockId, cascadeDelete: true)
                .ForeignKey("dbo.ClientTask", t => t.TaskId, cascadeDelete: true)
                .Index(t => t.ClientBuildingBlockId)
                .Index(t => t.TaskId);
            
            CreateTable(
                "dbo.ClientTask",
                c => new
                    {
                        Id = c.Guid(nullable: false),
                        Name = c.String(),
                        DeadlineDate = c.DateTime(nullable: false),
                        Content = c.String(),
                        Status = c.Int(nullable: false),
                        Feedback = c.String(),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.AspNetRoles",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.Name, unique: true, name: "RoleNameIndex");
            
            CreateTable(
                "dbo.AspNetUsers",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Email = c.String(maxLength: 256),
                        EmailConfirmed = c.Boolean(nullable: false),
                        PasswordHash = c.String(),
                        SecurityStamp = c.String(),
                        PhoneNumber = c.String(),
                        PhoneNumberConfirmed = c.Boolean(nullable: false),
                        TwoFactorEnabled = c.Boolean(nullable: false),
                        LockoutEndDateUtc = c.DateTime(),
                        LockoutEnabled = c.Boolean(nullable: false),
                        AccessFailedCount = c.Int(nullable: false),
                        UserName = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)
                .Index(t => t.UserName, unique: true, name: "UserNameIndex");
            
            CreateTable(
                "dbo.Caregiver",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.User", t => t.Id)
                .Index(t => t.Id);
            
            CreateTable(
                "dbo.Client",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Points = c.Int(nullable: false),
                        CaregiverId = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.User", t => t.Id)
                .ForeignKey("dbo.Caregiver", t => t.CaregiverId)
                .Index(t => t.Id)
                .Index(t => t.CaregiverId);
            
            CreateTable(
                "dbo.Relative",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        ClientId = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.User", t => t.Id)
                .ForeignKey("dbo.Client", t => t.ClientId)
                .Index(t => t.Id)
                .Index(t => t.ClientId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Relative", "ClientId", "dbo.Client");
            DropForeignKey("dbo.Relative", "Id", "dbo.User");
            DropForeignKey("dbo.Client", "CaregiverId", "dbo.Caregiver");
            DropForeignKey("dbo.Client", "Id", "dbo.User");
            DropForeignKey("dbo.Caregiver", "Id", "dbo.User");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropForeignKey("dbo.ClientComponent", "TaskId", "dbo.ClientTask");
            DropForeignKey("dbo.ClientComponent", "ClientBuildingBlockId", "dbo.ClientBuildingBlock");
            DropForeignKey("dbo.ClientBuildingBlock", "ClientId", "dbo.Client");
            DropForeignKey("dbo.ClientBuildingBlock", "BuildingblockId", "dbo.Buildingblock");
            DropForeignKey("dbo.Buildingblock", "Client_Id", "dbo.Client");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.User");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.User");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.User");
            DropForeignKey("dbo.Activity", "ComponentId", "dbo.Component");
            DropForeignKey("dbo.UsefulLink", "ComponentId", "dbo.Component");
            DropForeignKey("dbo.Component", "BuildingblockId", "dbo.Buildingblock");
            DropIndex("dbo.Relative", new[] { "ClientId" });
            DropIndex("dbo.Relative", new[] { "Id" });
            DropIndex("dbo.Client", new[] { "CaregiverId" });
            DropIndex("dbo.Client", new[] { "Id" });
            DropIndex("dbo.Caregiver", new[] { "Id" });
            DropIndex("dbo.AspNetUsers", "UserNameIndex");
            DropIndex("dbo.AspNetRoles", "RoleNameIndex");
            DropIndex("dbo.ClientComponent", new[] { "TaskId" });
            DropIndex("dbo.ClientComponent", new[] { "ClientBuildingBlockId" });
            DropIndex("dbo.ClientBuildingBlock", new[] { "ClientId" });
            DropIndex("dbo.ClientBuildingBlock", new[] { "BuildingblockId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.UsefulLink", new[] { "ComponentId" });
            DropIndex("dbo.Buildingblock", new[] { "Client_Id" });
            DropIndex("dbo.Component", new[] { "BuildingblockId" });
            DropIndex("dbo.Activity", new[] { "ComponentId" });
            DropTable("dbo.Relative");
            DropTable("dbo.Client");
            DropTable("dbo.Caregiver");
            DropTable("dbo.AspNetUsers");
            DropTable("dbo.AspNetRoles");
            DropTable("dbo.ClientTask");
            DropTable("dbo.ClientComponent");
            DropTable("dbo.ClientBuildingBlock");
            DropTable("dbo.AspNetUserRoles");
            DropTable("dbo.AspNetUserLogins");
            DropTable("dbo.AspNetUserClaims");
            DropTable("dbo.User");
            DropTable("dbo.UsefulLink");
            DropTable("dbo.Buildingblock");
            DropTable("dbo.Component");
            DropTable("dbo.Activity");
        }
    }
}
