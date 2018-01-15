﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models;
using Patterns.Repository;

namespace ImReady.Service.Services
{
    public class BuildingblockService : IBuildingblockService
    {
        private readonly IImReadyUnitOfWork _unitOfWork;
        private readonly IRepository<Buildingblock> _buildingBlockRepository;
        private readonly IRepository<Component> _componentRepository;

		public BuildingblockService (IImReadyUnitOfWork unitOfWork)
        {
            _unitOfWork = unitOfWork;

            _buildingBlockRepository = _unitOfWork.BuildingblockRepository;
			_componentRepository = _unitOfWork.ComponentRepository;

		}
        public bool AddBlock(Buildingblock block)
        {
            _buildingBlockRepository.Add(block);
            _unitOfWork.Commit();
            return true;
        }
		
		public bool EditBlock(Buildingblock block)
        {
            Buildingblock buildingblock = _buildingBlockRepository.Entities.FirstOrDefault(s => s.Id == block.Id);
            buildingblock.Description = block.Description;
            buildingblock.Name = block.Name;
            buildingblock.Type = block.Type;

            _unitOfWork.Commit();
            return true;
        }

        public ICollection<Buildingblock> getAll()
        {
            return _buildingBlockRepository.Entities.Where(x => !x.Deleted).ToList();
        }

        public Buildingblock getById(string Id)
        {
            return _buildingBlockRepository.Entities.FirstOrDefault(s => s.Id == Id);
        }

        public bool RemoveBlock(string Id)
        {
            var block = _buildingBlockRepository.Entities.FirstOrDefault(s => s.Id == Id);
            _buildingBlockRepository.Remove(block);
            _unitOfWork.Commit();
            return true;
        }
	}
}
