using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services
{
    public class BuildingBlockMockService
    {
        public List<BuildingBlock> GetBlocks(bool mock = true)
        {
            if(mock)
            {
                return new List<BuildingBlock>()
                {
                    new BuildingBlock()
                    {
                        BuildingBlockType = BuildingBlockType.Geld,
                        Naam = "Geld",
                    },
                    new BuildingBlock()
                    {
                        BuildingBlockType = BuildingBlockType.Huis,
                        Naam = "Huis"
                    },
                    new BuildingBlock()
                    {
                        BuildingBlockType = BuildingBlockType.Werk,
                        Naam = "Werk"
                    }
                };
            }
            else
            {
                throw new NotImplementedException();
            }
        }
    }
}
