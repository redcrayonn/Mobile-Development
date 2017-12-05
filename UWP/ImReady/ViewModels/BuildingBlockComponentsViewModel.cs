using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.ViewModels
{
    public class BuildingBlockComponentsViewModel
    {
        public static BuildingBlockComponentsViewModel SingleInstance => new BuildingBlockComponentsViewModel();

        public BuildingBlock BuildingBlock { get; set; }
    }
}
