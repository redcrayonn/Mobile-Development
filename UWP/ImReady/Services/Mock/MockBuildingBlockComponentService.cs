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

        public Component GetComponent(int blockId)
        {
            return new Component()
            {
                Name = "Woonruimte zoeken",
                Activities = new Activity[]
                {
                    new ActivityService().GetActivity(-1),
                    new ActivityService().GetActivity(-1),
                }
            };
        }
    }
}
