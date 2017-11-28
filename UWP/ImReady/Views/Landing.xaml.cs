using ImReady.Views.Login;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Storage;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace ImReady.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class Landing : Page
    {
        public Landing()
        {
            this.InitializeComponent();
            //NavigateToLogin();
            this.Loaded += Landing_Loaded;
            progress1.IsActive = true;
            progress1.Visibility = Visibility.Visible;
        }

        private async void Landing_Loaded(object sender, RoutedEventArgs e)
        {
            var file = await StorageFile.GetFileFromApplicationUriAsync(new Uri("ms-appx:///Assets/SharedResources/Logo.svg"));

            await SvgControl.LoadFileAsync(file);
        }

        public async void NavigateToLogin()
        {
            progress1.IsActive = true;
            progress1.Visibility = Visibility.Visible;
            await Task.Delay(2000);
            await this.Dispatcher.RunAsync(Windows.UI.Core.CoreDispatcherPriority.Normal, () =>
            {
                Frame.Navigate(typeof(LoginMain));
            });
        }

        protected override void OnNavigatingFrom(NavigatingCancelEventArgs e)
        {
            this.SvgControl.SafeUnload();
        }

    }
}
