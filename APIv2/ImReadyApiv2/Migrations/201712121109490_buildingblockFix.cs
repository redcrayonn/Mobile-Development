namespace ImReadyApiv2.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class buildingblockFix : DbMigration
    {
        public override void Up()
        {
            DropIndex("dbo.Buildingblock", new[] { "Client_Id" });
            DropColumn("dbo.ClientBuildingBlock", "ClientId");
            RenameColumn(table: "dbo.ClientBuildingBlock", name: "Client_Id", newName: "ClientId");
            DropColumn("dbo.Buildingblock", "Client_Id");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Buildingblock", "Client_Id", c => c.String(maxLength: 128));
            RenameColumn(table: "dbo.ClientBuildingBlock", name: "ClientId", newName: "Client_Id");
            AddColumn("dbo.ClientBuildingBlock", "ClientId", c => c.String(maxLength: 128));
            CreateIndex("dbo.Buildingblock", "Client_Id");
        }
    }
}
