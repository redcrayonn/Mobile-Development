namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Added_Feedback_Deadline : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Feedback", "Sent", c => c.DateTime(nullable: false));
        }
        
        public override void Down()
        {
            DropColumn("dbo.Feedback", "Sent");
        }
    }
}
