using System;
using ImReady.Services.Interfaces;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Models;

namespace ImReady.Services.Mock
{
    public class MockBuildingBlockService : IBuildingBlockService
    {
        public bool Mock => true;

        public List<BuildingBlock> GetAllBlocks()
        {
            return new List<BuildingBlock>()
            {
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Geld,
                    Name = "Geld",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                        new BuildingBlockComponentService().GetComponent(-1),
                        new BuildingBlockComponentService().GetComponent(-1),
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Huis,
                    Name = "Huis",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Werk,
                    Name = "Werk",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Geld,
                    Name = "Geld",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Huis,
                    Name = "Huis",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Werk,
                    Name = "Werk",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Geld,
                    Name = "Geld",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Huis,
                    Name = "Huis",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },
                new BuildingBlock()
                {
                    BuildingBlockType = BuildingBlockType.Werk,
                    Name = "Werk",
                    Image = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    Components = new List<BuildingBlockComponent>(){
                        new BuildingBlockComponentService().GetComponent(-1),
                    },
                },

            };
        }
    }
}
