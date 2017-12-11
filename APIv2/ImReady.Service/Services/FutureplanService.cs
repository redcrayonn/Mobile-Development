using ImReady.Data.Models;
using ImReady.Service.Services.Interfaces;
using Patterns.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Service.Services
{
    public class FutureplanService : IFutureplanService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<ClientBuildingBlock> _buildingBlockRepository;

        public FutureplanService(IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _buildingBlockRepository = _unitOfWork.ClientBuildingblockRepository;
        }

        public List<ClientBuildingBlock> GetPlan(string clientId)
        {
            var blocks = _buildingBlockRepository.Entities
                            .Where(w => w.ClientId == clientId && !w.Deleted).ToList();
            return blocks;
        }
    }
}
