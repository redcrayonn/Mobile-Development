using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Media.Imaging;

namespace ImReady.Services
{
    public class BuildingBlockMockService
    {
        //TODO: in aparte mock service plaatsen
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
                        Img = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    },
                    new BuildingBlock()
                    {
                        BuildingBlockType = BuildingBlockType.Huis,
                        Naam = "Huis",
                        Img = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
                    },
                    new BuildingBlock()
                    {
                        BuildingBlockType = BuildingBlockType.Werk,
                        Naam = "Werk",
                        Img = "ms-appx:///Assets/Material/ic_home_white_48dp.png",
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
