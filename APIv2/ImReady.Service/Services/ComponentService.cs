using ImReady.Service.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Data.Models;
using Patterns.Repository;

namespace ImReady.Service.Services {
	public class ComponentService : IComponentService {
		private readonly IImReadyUnitOfWork _unitOfWork;
		private readonly IRepository<Component> _componentRepository;
		private readonly IRepository<UsefulLink> _usefulLinkRepository;

		public ComponentService (IImReadyUnitOfWork unitOfWork) {
			_unitOfWork = unitOfWork;

			_componentRepository = _unitOfWork.ComponentRepository;
			_usefulLinkRepository = _unitOfWork.UsefulLinkRepository;
		}

		public void Create (Component component) {
			_componentRepository.Add(component);
			_unitOfWork.Commit();
		}

		public Component Get (string componentId) {
			return _componentRepository.Entities.SingleOrDefault(c => c.Id == componentId);
		}

		public bool Update (Component component) {
			Component editComponent = Get(component.Id);

			if (editComponent == null) {
				return false;
			}

			List<UsefulLink> usefulLinks = _usefulLinkRepository.Entities.Where(u => u.ComponentId == component.Id).ToList();
			foreach(UsefulLink usefulLink in usefulLinks) {
				_usefulLinkRepository.Remove(usefulLink);
			}

			editComponent.Name = component.Name;
			editComponent.Description = component.Description;

			foreach (UsefulLink usefulLink in component.UsefulLinks) {
				editComponent.UsefulLinks.Add(usefulLink);
				usefulLink.Component = editComponent;
			}

			editComponent.YoutubeURL = component.YoutubeURL;

			_unitOfWork.Commit();

			return true;
		}
	}
}
