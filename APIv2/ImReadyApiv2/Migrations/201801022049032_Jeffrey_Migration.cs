namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Jeffrey_Migration : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.ClientComponent", "TaskId", "dbo.ClientTask");
            DropIndex("dbo.ClientComponent", new[] { "TaskId" });
            CreateTable(
                "dbo.Calendars",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        UserId = c.String(maxLength: 128),
                        Title = c.String(nullable: false),
                        StartDate = c.DateTime(nullable: false),
                        EndDate = c.DateTime(nullable: false),
                        Location = c.String(),
                        Remark = c.String(),
                        RelatedCalendarId = c.String(maxLength: 128),
                        Deleted = c.Boolean(nullable: false),
                        ClientTask_Id = c.String(maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.Calendars", t => t.RelatedCalendarId)
                .ForeignKey("dbo.ClientTask", t => t.ClientTask_Id)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId)
                .Index(t => t.UserId)
                .Index(t => t.RelatedCalendarId)
                .Index(t => t.ClientTask_Id);
            
            AddColumn("dbo.UsefulLink", "ClientComponent_Id", c => c.String(maxLength: 128));
            AddColumn("dbo.ClientComponent", "YoutubeUrl", c => c.String());
            CreateIndex("dbo.UsefulLink", "ClientComponent_Id");
            AddForeignKey("dbo.UsefulLink", "ClientComponent_Id", "dbo.ClientComponent", "Id");
            DropColumn("dbo.ClientComponent", "TaskId");

			AddColumn("dbo.ClientTask", "Description", c => c.String(maxLength: 128));
			AddColumn("dbo.ClientTask", "ClientComponentId", c => c.String(maxLength: 128));
			AddForeignKey("dbo.ClientTask", "ClientComponentId", "dbo.ClientComponent", "Id");
		}
        
        public override void Down()
        {
            AddColumn("dbo.ClientComponent", "TaskId", c => c.String(maxLength: 128));
            DropForeignKey("dbo.Calendars", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.UsefulLink", "ClientComponent_Id", "dbo.ClientComponent");
            DropForeignKey("dbo.ClientTask", "ClientComponentId", "dbo.ClientComponent");
            DropForeignKey("dbo.Calendars", "ClientTask_Id", "dbo.ClientTask");
            DropForeignKey("dbo.Calendars", "RelatedCalendarId", "dbo.Calendars");
            DropIndex("dbo.ClientTask", new[] { "ClientComponentId" });
            DropIndex("dbo.Calendars", new[] { "ClientTask_Id" });
            DropIndex("dbo.Calendars", new[] { "RelatedCalendarId" });
            DropIndex("dbo.Calendars", new[] { "UserId" });
            DropIndex("dbo.UsefulLink", new[] { "ClientComponent_Id" });
            DropColumn("dbo.ClientComponent", "YoutubeUrl");
            DropColumn("dbo.UsefulLink", "ClientComponent_Id");
            DropTable("dbo.Calendars");
            CreateIndex("dbo.ClientComponent", "TaskId");
            AddForeignKey("dbo.ClientComponent", "TaskId", "dbo.ClientTask", "Id");
        }
    }
}
