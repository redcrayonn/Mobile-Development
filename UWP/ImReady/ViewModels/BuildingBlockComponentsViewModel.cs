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
    public class BuildingBlockComponentsViewModel
    {
        public static BuildingBlockComponentsViewModel SingleInstance => new BuildingBlockComponentsViewModel();

        public BuildingBlock BuildingBlock { get; set; }

        public BuildingBlockComponentsViewModel()
        {
            Items.Add(new MyItemViewModel("Item1"));
            Items.Add(new MyItemViewModel("Item2"));
            Items.Add(new MyItemViewModel("Item3"));
        }

        public ApplicationDataLocality ApplicationDataLocalityEnum { get; } =
           ApplicationDataLocality.Local;

        public FontStyle FontStyleEnum { get; } =
                   FontStyle.Normal;

        public ObservableCollection<MyItemViewModel> Items { get; set; } = new ObservableCollection<MyItemViewModel>();
    }
}
