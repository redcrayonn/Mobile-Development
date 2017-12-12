using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public interface IBuildingblockService
    {
        ICollection<Buildingblock> getAll();
        Buildingblock getById(string Id);
        bool AddBlock(Buildingblock block);
        bool RemoveBlock(string Id);
        bool EditBlock(Buildingblock block);
    }
}
