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
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace ImReady.Views.Login
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class LoginMain : Page
    {
        public LoginMain()
        {
            this.InitializeComponent();
            LogoSvg.Source = new SvgImageSource(new Uri("ms-appx:///Assets/SharedResources/Logo.svg"));
        }

        private void LoginSubmit_Click(object sender, RoutedEventArgs e)
        {
            //Check if user has stored credentials, if so login.

            //If not --> show Login UI.


            //Frame.Navigate(typeof(Home.HomeMain));
        }
    }
}
