using ImReady.Models;
using ImReady.ViewModels;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Core;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace ImReady.Views.BlockComponents
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class BuildingBlockComponents : Page
    {
        private ComponentsViewModel ViewModel = ComponentsViewModel.SingleInstance;

        public BuildingBlockComponents()
        {
            this.InitializeComponent();
            SystemNavigationManager.GetForCurrentView().AppViewBackButtonVisibility = AppViewBackButtonVisibility.Visible;
            DataContext = ViewModel;
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            var article = e.Parameter as BuildingBlock;
            if (e.Parameter != null && e.Parameter is BuildingBlock)
            {
                ViewModel = new ComponentsViewModel()
                {
                    BuildingBlock = e.Parameter as BuildingBlock
                };
            }
        }

        private void AppBarButton_Click(object sender, RoutedEventArgs e)
        {

        }
    }
}
