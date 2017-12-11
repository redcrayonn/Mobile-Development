namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Added_Deadline_ClientBlock_And_ClientComponent : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.ClientComponent", "Deadline", c => c.DateTime(nullable: false));
            AlterColumn("dbo.User", "Lastname", c => c.String(nullable: false));
            DropColumn("dbo.User", "Role");
        }
        
        public override void Down()
        {
            AddColumn("dbo.User", "Role", c => c.Int(nullable: false));
            AlterColumn("dbo.User", "Lastname", c => c.String());
            DropColumn("dbo.ClientComponent", "Deadline");
        }
    }
}
