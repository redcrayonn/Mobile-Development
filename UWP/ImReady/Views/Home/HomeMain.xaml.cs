using ImReady.Models;
using ImReady.ViewModels;
using ImReady.Views.BuildingBlockComponents;
using System;
using System.Collections.Generic;
using System.ComponentModel;
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
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace ImReady.Views.Home
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class HomeMain : Page
    {
        private HomeMainViewModel ViewModel => HomeMainViewModel.SingleInstance;

        public HomeMain()
        {
            this.InitializeComponent();
            DataContext = ViewModel;
        }

        private void AppBarButton_Click(object sender, RoutedEventArgs e)
        {
            
        }

        private void HamburgerButton_Click(object sender, RoutedEventArgs e)
        {
            MySplitView.IsPaneOpen = !MySplitView.IsPaneOpen;
        }

        private void GridView_ItemClick(object sender, ItemClickEventArgs e)
        {
            if(!((e.ClickedItem as BuildingBlock).GetBlockType() == BuildingBlockType.Add))
            {
                ViewModel.NavigateToBuildingBlockComponents.Execute((e.ClickedItem as BuildingBlock));
            }
        }

        private void RelativePanel_Tapped(object sender, TappedRoutedEventArgs e)
        {
            //ViewModel.NavigateToAddComponent.Execute((e.OriginalSource));
            Frame.Navigate(typeof(AddBuildingBlockComponents));
        }
    }
}
