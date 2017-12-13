namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Added_Notifications : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Notifications",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        ReceiverId = c.String(maxLength: 128),
                        SenderId = c.String(maxLength: 128),
                        IsRead = c.Boolean(nullable: false),
                        CreatedDate = c.DateTime(nullable: false),
                        Type = c.Int(nullable: false),
                        ObjectId = c.String(),
                        Deleted = c.Boolean(nullable: false),
                        User_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.User_Id)
                .ForeignKey("dbo.AspNetUsers", t => t.ReceiverId)
                .ForeignKey("dbo.AspNetUsers", t => t.SenderId)
                .Index(t => t.ReceiverId)
                .Index(t => t.SenderId)
                .Index(t => t.User_Id);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Notifications", "SenderId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Notifications", "ReceiverId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Notifications", "User_Id", "dbo.AspNetUsers");
            DropIndex("dbo.Notifications", new[] { "User_Id" });
            DropIndex("dbo.Notifications", new[] { "SenderId" });
            DropIndex("dbo.Notifications", new[] { "ReceiverId" });
            DropTable("dbo.Notifications");
        }
    }
}
