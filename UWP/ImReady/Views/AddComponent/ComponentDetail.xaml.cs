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

namespace ImReady.Views.AddComponent
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class ComponentDetail : Page
    {
        private ComponentDetailViewModel ViewModel = ComponentDetailViewModel.SingleInstance;

        public ComponentDetail()
        {
            this.InitializeComponent();
            DataContext = ViewModel;
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            var article = e.Parameter as Component;
            if (e.Parameter != null && e.Parameter is Component)
            {
                ViewModel.Component = (e.Parameter as Component);
            }

            //Om een of andere reden zorgt de logica die in App.cs gebruikt wordt tot een back button in deze view. TODO: uitzoeken waarom 
            var frame = Window.Current.Content as Frame;
            SystemNavigationManager.GetForCurrentView().AppViewBackButtonVisibility = frame.CanGoBack ?
                          AppViewBackButtonVisibility.Visible :
                          AppViewBackButtonVisibility.Collapsed;
        }
    }
}
