namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class newInitial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Activity",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(),
                        Description = c.String(),
                        Points = c.Int(nullable: false),
                        ComponentId = c.String(maxLength: 128),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Component", t => t.ComponentId)
                .Index(t => t.ComponentId);
            
            CreateTable(
                "dbo.Component",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        BuildingblockId = c.String(maxLength: 128),
                        Name = c.String(),
                        Description = c.String(),
                        YoutubeURL = c.String(),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Buildingblock", t => t.BuildingblockId)
                .Index(t => t.BuildingblockId);
            
            CreateTable(
                "dbo.Buildingblock",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(),
                        Description = c.String(),
                        Type = c.Int(nullable: false),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.UsefulLink",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        ComponentId = c.String(maxLength: 128),
                        Url = c.String(),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Component", t => t.ComponentId)
                .Index(t => t.ComponentId);
            
            CreateTable(
                "dbo.AspNetUsers",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        FirstName = c.String(),
                        LastName = c.String(nullable: false),
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
                "dbo.AspNetUserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.String(nullable: false, maxLength: 128),
                        ClaimType = c.String(),
                        ClaimValue = c.String(),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.ClientBuildingBlock",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        BuildingblockId = c.String(maxLength: 128),
                        ClientId = c.String(maxLength: 128),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Buildingblock", t => t.BuildingblockId)
                .ForeignKey("dbo.Client", t => t.ClientId)
                .Index(t => t.BuildingblockId)
                .Index(t => t.ClientId);
            
            CreateTable(
                "dbo.ClientComponent",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Description = c.String(),
                        Deadline = c.DateTime(nullable: false),
                        ClientBuildingBlockId = c.String(maxLength: 128),
                        Status = c.Int(nullable: false),
                        ComponentId = c.String(maxLength: 128),
                        TaskId = c.String(maxLength: 128),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.ClientBuildingBlock", t => t.ClientBuildingBlockId)
                .ForeignKey("dbo.Component", t => t.ComponentId)
                .ForeignKey("dbo.ClientTask", t => t.TaskId)
                .Index(t => t.ClientBuildingBlockId)
                .Index(t => t.ComponentId)
                .Index(t => t.TaskId);
            
            CreateTable(
                "dbo.ClientActivity",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        ClientComponentId = c.String(maxLength: 128),
                        ActivityId = c.String(maxLength: 128),
                        Status = c.Int(nullable: false),
                        Content = c.String(),
                        Deadline = c.DateTime(nullable: false),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Activity", t => t.ActivityId)
                .ForeignKey("dbo.ClientComponent", t => t.ClientComponentId)
                .Index(t => t.ClientComponentId)
                .Index(t => t.ActivityId);
            
            CreateTable(
                "dbo.Feedback",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        CaregiverId = c.String(maxLength: 128),
                        ClientActivityId = c.String(maxLength: 128),
                        Content = c.String(),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Caregiver", t => t.CaregiverId)
                .ForeignKey("dbo.ClientActivity", t => t.ClientActivityId)
                .Index(t => t.CaregiverId)
                .Index(t => t.ClientActivityId);
            
            CreateTable(
                "dbo.ClientTask",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(),
                        DeadlineDate = c.DateTime(nullable: false),
                        Content = c.String(),
                        Status = c.Int(nullable: false),
                        Feedback = c.String(),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.AspNetUserLogins",
                c => new
                    {
                        LoginProvider = c.String(nullable: false, maxLength: 128),
                        ProviderKey = c.String(nullable: false, maxLength: 128),
                        UserId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.LoginProvider, t.ProviderKey, t.UserId })
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
                .ForeignKey("dbo.AspNetRoles", t => t.RoleId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);
            
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
                "dbo.Caregiver",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.Id)
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
                .ForeignKey("dbo.AspNetUsers", t => t.Id)
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
                .ForeignKey("dbo.AspNetUsers", t => t.Id)
                .ForeignKey("dbo.Client", t => t.ClientId)
                .Index(t => t.Id)
                .Index(t => t.ClientId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Relative", "ClientId", "dbo.Client");
            DropForeignKey("dbo.Relative", "Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.Client", "CaregiverId", "dbo.Caregiver");
            DropForeignKey("dbo.Client", "Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.Caregiver", "Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropForeignKey("dbo.ClientComponent", "TaskId", "dbo.ClientTask");
            DropForeignKey("dbo.ClientComponent", "ComponentId", "dbo.Component");
            DropForeignKey("dbo.ClientComponent", "ClientBuildingBlockId", "dbo.ClientBuildingBlock");
            DropForeignKey("dbo.Feedback", "ClientActivityId", "dbo.ClientActivity");
            DropForeignKey("dbo.Feedback", "CaregiverId", "dbo.Caregiver");
            DropForeignKey("dbo.ClientActivity", "ClientComponentId", "dbo.ClientComponent");
            DropForeignKey("dbo.ClientActivity", "ActivityId", "dbo.Activity");
            DropForeignKey("dbo.ClientBuildingBlock", "ClientId", "dbo.Client");
            DropForeignKey("dbo.ClientBuildingBlock", "BuildingblockId", "dbo.Buildingblock");
            DropForeignKey("dbo.UsefulLink", "ComponentId", "dbo.Component");
            DropForeignKey("dbo.Component", "BuildingblockId", "dbo.Buildingblock");
            DropForeignKey("dbo.Activity", "ComponentId", "dbo.Component");
            DropIndex("dbo.Relative", new[] { "ClientId" });
            DropIndex("dbo.Relative", new[] { "Id" });
            DropIndex("dbo.Client", new[] { "CaregiverId" });
            DropIndex("dbo.Client", new[] { "Id" });
            DropIndex("dbo.Caregiver", new[] { "Id" });
            DropIndex("dbo.AspNetRoles", "RoleNameIndex");
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.Feedback", new[] { "ClientActivityId" });
            DropIndex("dbo.Feedback", new[] { "CaregiverId" });
            DropIndex("dbo.ClientActivity", new[] { "ActivityId" });
            DropIndex("dbo.ClientActivity", new[] { "ClientComponentId" });
            DropIndex("dbo.ClientComponent", new[] { "TaskId" });
            DropIndex("dbo.ClientComponent", new[] { "ComponentId" });
            DropIndex("dbo.ClientComponent", new[] { "ClientBuildingBlockId" });
            DropIndex("dbo.ClientBuildingBlock", new[] { "ClientId" });
            DropIndex("dbo.ClientBuildingBlock", new[] { "BuildingblockId" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.AspNetUsers", "UserNameIndex");
            DropIndex("dbo.UsefulLink", new[] { "ComponentId" });
            DropIndex("dbo.Component", new[] { "BuildingblockId" });
            DropIndex("dbo.Activity", new[] { "ComponentId" });
            DropTable("dbo.Relative");
            DropTable("dbo.Client");
            DropTable("dbo.Caregiver");
            DropTable("dbo.AspNetRoles");
            DropTable("dbo.AspNetUserRoles");
            DropTable("dbo.AspNetUserLogins");
            DropTable("dbo.ClientTask");
            DropTable("dbo.Feedback");
            DropTable("dbo.ClientActivity");
            DropTable("dbo.ClientComponent");
            DropTable("dbo.ClientBuildingBlock");
            DropTable("dbo.AspNetUserClaims");
            DropTable("dbo.AspNetUsers");
            DropTable("dbo.UsefulLink");
            DropTable("dbo.Buildingblock");
            DropTable("dbo.Component");
            DropTable("dbo.Activity");
        }
    }
}
