using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using ImReady.ViewModels;
using ImReady.Models;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace ImReady.Views.AddComponent
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class AddBuildingBlockComponents : Page
    {
        private AddComponentViewModel ViewModel = AddComponentViewModel.SingleInstance;

        public AddBuildingBlockComponents()
        {
            this.InitializeComponent();
            this.DataContext = ViewModel;
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            ViewModel = AddComponentViewModel.SingleInstance;
        }

        private void StackPanel_Tapped(object sender, TappedRoutedEventArgs e)
        {
            Frame.Navigate(typeof(AddComponent), ((e.OriginalSource as TextBlock).DataContext as BuildingBlock));
        }
    }
}
