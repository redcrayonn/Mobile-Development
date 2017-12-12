using ImReady.Helpers;
using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Storage;
using Windows.UI.Text;

namespace ImReady.ViewModels
{
    public class BuildingBlockComponentsViewModel : BindableBase
    {
        public static BuildingBlockComponentsViewModel SingleInstance => new BuildingBlockComponentsViewModel();

        public BuildingBlock BuildingBlock { get; set; }

        public BuildingBlockComponentsViewModel()
        {

        }
    }
}
