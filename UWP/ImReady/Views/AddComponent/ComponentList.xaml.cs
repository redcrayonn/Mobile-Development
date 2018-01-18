using ImReady.Models;
using ImReady.Services.Web;
using ImReady.ViewModels;
using ImReady.Views.Home;
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

namespace ImReady.Views.AddComponent
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class AddableComponentsList : Page
    {
        private AddableComponentsListViewModel ViewModel = AddableComponentsListViewModel.SingleInstance;

        public AddableComponentsList()
        {
            this.InitializeComponent();
            this.DataContext = ViewModel;
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            var article = e.Parameter as BuildingBlock;
            if (e.Parameter != null && e.Parameter is BuildingBlock)
            {
                ViewModel.Components = (e.Parameter as BuildingBlock).Components;
            }

            //Om een of andere reden zorgt de logica die in App.cs gebruikt wordt tot een back button in deze view. TODO: uitzoeken waarom 
            var frame = Window.Current.Content as Frame;
            SystemNavigationManager.GetForCurrentView().AppViewBackButtonVisibility = frame.CanGoBack ?
                          AppViewBackButtonVisibility.Visible :
                          AppViewBackButtonVisibility.Collapsed;
        }

        private void Grid_Tapped(object sender, TappedRoutedEventArgs e)
        {
            var component = (e.OriginalSource as FrameworkElement).DataContext as Component;
            Frame.Navigate(typeof(ComponentDetail), component);
        }
    }
}
