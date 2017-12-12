using ImReady.Models;
using ImReady.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services.Mock
{
    public class MockBuildingBlockComponentService : IBuildingBlockComponentService
    {
        public bool Mock => true;

        public BuildingBlockComponent GetComponent(int blockId)
        {
            return new BuildingBlockComponent()
            {
                ComponentName = "Woonruimte zoeken",
                Activities = new List<Activity>()
                {
                    new ActivityService().GetActivity(-1),
                }
            };
        }
    }
}
