using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Services.Interfaces
{
    public interface IBuildingBlockComponentService : IBaseService
    {
        BuildingBlockComponent GetComponent(int blockId);
    }
}
