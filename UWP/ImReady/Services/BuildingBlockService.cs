using ImReady.Config;
using ImReady.Models;
using ImReady.Services.Interfaces;
using ImReady.Services.Mock;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Media.Imaging;

namespace ImReady.Services
{
    public class BuildingBlockMockService : IBuildingBlockService
    {
        private IBuildingBlockService MockService => new MockBuildingBlockService();
        private bool Mock = GlobalConfig.MockServices;

        public List<BuildingBlock> GetAllBlocks()
        {
            if(Mock)
            {
                return MockService.GetAllBlocks();
            }
            else
            {
                throw new NotImplementedException();
            }
        }
    }
}
