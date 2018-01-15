using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input {
	public class PostComponentInputModel {
		[Required]
		public string Id { get; set; }

		[Required]
		public string Name { get; set; }

		[Required]
		public string Description { get; set; }

		public string YoutubeURL { get; set; }
		public List<string> UsefulLinks { get; set; }

		internal Component GetModel (Buildingblock block) {
			Component component = new Component();
			component.Id = Id;
			component.BuildingblockId = block.Id;
			component.Block = block;
			component.Name = Name;
			component.Description = Description;
			component.YoutubeURL = YoutubeURL;

			component.UsefulLinks = new List<UsefulLink>();
			if (UsefulLinks != null) {
				foreach (string url in UsefulLinks) {
					UsefulLink link = new UsefulLink();
					link.Component = component;
					link.ComponentId = component.Id;
					link.Url = url;

					component.UsefulLinks.Add(link);
				}
			}

			component.Activities = null;

			return component;
		}
	}
}