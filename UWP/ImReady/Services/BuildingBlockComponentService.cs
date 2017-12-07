using ImReady.Config;
using ImReady.Models;
using ImReady.Services.Interfaces;
using ImReady.Services.Mock;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services
{
    public class BuildingBlockComponentService : BaseService, IBuildingBlockComponentService
    {
        private IBuildingBlockComponentService MockService => new MockBuildingBlockComponentService();

        public BuildingBlockComponent GetComponent(int blockId)
        {
            if (Mock)
            {
                return MockService.GetComponent(-1);
            }
            else
            {
                throw new NotImplementedException();
            }
        }
    }
}
