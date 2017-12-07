namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class identityFix : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.User");
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers");
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.AspNetUsers", "UserNameIndex");
            AlterColumn("dbo.User", "Email", c => c.String(maxLength: 256));
            AlterColumn("dbo.User", "UserName", c => c.String(nullable: false, maxLength: 256));
            AlterColumn("dbo.AspNetUserClaims", "UserId", c => c.String(maxLength: 128));
            CreateIndex("dbo.User", "UserName", unique: true, name: "UserNameIndex");
            CreateIndex("dbo.AspNetUserClaims", "UserId");
            CreateIndex("dbo.AspNetUsers", "Id");
            AddForeignKey("dbo.AspNetUsers", "Id", "dbo.User", "Id");
            AddForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.User", "Id");
            DropColumn("dbo.AspNetUsers", "Email");
            DropColumn("dbo.AspNetUsers", "EmailConfirmed");
            DropColumn("dbo.AspNetUsers", "PasswordHash");
            DropColumn("dbo.AspNetUsers", "SecurityStamp");
            DropColumn("dbo.AspNetUsers", "PhoneNumber");
            DropColumn("dbo.AspNetUsers", "PhoneNumberConfirmed");
            DropColumn("dbo.AspNetUsers", "TwoFactorEnabled");
            DropColumn("dbo.AspNetUsers", "LockoutEndDateUtc");
            DropColumn("dbo.AspNetUsers", "LockoutEnabled");
            DropColumn("dbo.AspNetUsers", "AccessFailedCount");
            DropColumn("dbo.AspNetUsers", "UserName");
        }
        
        public override void Down()
        {
            AddColumn("dbo.AspNetUsers", "UserName", c => c.String(nullable: false, maxLength: 256));
            AddColumn("dbo.AspNetUsers", "AccessFailedCount", c => c.Int(nullable: false));
            AddColumn("dbo.AspNetUsers", "LockoutEnabled", c => c.Boolean(nullable: false));
            AddColumn("dbo.AspNetUsers", "LockoutEndDateUtc", c => c.DateTime());
            AddColumn("dbo.AspNetUsers", "TwoFactorEnabled", c => c.Boolean(nullable: false));
            AddColumn("dbo.AspNetUsers", "PhoneNumberConfirmed", c => c.Boolean(nullable: false));
            AddColumn("dbo.AspNetUsers", "PhoneNumber", c => c.String());
            AddColumn("dbo.AspNetUsers", "SecurityStamp", c => c.String());
            AddColumn("dbo.AspNetUsers", "PasswordHash", c => c.String());
            AddColumn("dbo.AspNetUsers", "EmailConfirmed", c => c.Boolean(nullable: false));
            AddColumn("dbo.AspNetUsers", "Email", c => c.String(maxLength: 256));
            DropForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.User");
            DropForeignKey("dbo.AspNetUsers", "Id", "dbo.User");
            DropIndex("dbo.AspNetUsers", new[] { "Id" });
            DropIndex("dbo.AspNetUserClaims", new[] { "UserId" });
            DropIndex("dbo.User", "UserNameIndex");
            AlterColumn("dbo.AspNetUserClaims", "UserId", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.User", "UserName", c => c.String());
            AlterColumn("dbo.User", "Email", c => c.String());
            CreateIndex("dbo.AspNetUsers", "UserName", unique: true, name: "UserNameIndex");
            CreateIndex("dbo.AspNetUserClaims", "UserId");
            AddForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.AspNetUsers", "Id", cascadeDelete: true);
            AddForeignKey("dbo.AspNetUserClaims", "UserId", "dbo.User", "Id", cascadeDelete: true);
        }
    }
}
