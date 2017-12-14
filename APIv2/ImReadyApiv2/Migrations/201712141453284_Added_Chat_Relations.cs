namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Added_Chat_Relations : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Chat",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        SenderId = c.String(maxLength: 128),
                        ReceiverId = c.String(maxLength: 128),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.ReceiverId)
                .ForeignKey("dbo.AspNetUsers", t => t.SenderId)
                .Index(t => t.SenderId)
                .Index(t => t.ReceiverId);
            
            CreateTable(
                "dbo.Message",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        ChatId = c.String(maxLength: 128),
                        Content = c.String(),
                        SentDate = c.DateTime(nullable: false),
                        Read = c.Boolean(nullable: false),
                        UserId = c.String(maxLength: 128),
                        Deleted = c.Boolean(nullable: false),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Chat", t => t.ChatId)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId)
                .Index(t => t.ChatId)
                .Index(t => t.UserId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Chat", "SenderId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Chat", "ReceiverId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Message", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Message", "ChatId", "dbo.Chat");
            DropIndex("dbo.Message", new[] { "UserId" });
            DropIndex("dbo.Message", new[] { "ChatId" });
            DropIndex("dbo.Chat", new[] { "ReceiverId" });
            DropIndex("dbo.Chat", new[] { "SenderId" });
            DropTable("dbo.Message");
            DropTable("dbo.Chat");
        }
    }
}
