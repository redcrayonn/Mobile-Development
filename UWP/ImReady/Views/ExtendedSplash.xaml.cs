using ImReady.Views.Login;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using System.Threading.Tasks;
using Windows.ApplicationModel.Activation;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.Storage;
using Windows.UI;
using Windows.UI.Core;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// The Blank Page item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace ImReady.Views
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class ExtendedSplash : Page  
    {  
        internal Rect splashImageRect; // Rect to store splash screen image coordinates.  
        internal bool dismissed = false; // Variable to track splash screen dismissal status.  
        internal Frame rootFrame;

        private SplashScreen splash; // Variable to hold the splash screen object.  
        private double ScaleFactor; //Variable to hold the device scale factor (use to determine phone screen resolution)  

        public ExtendedSplash(SplashScreen splashscreen, bool loadState)
        {
            InitializeComponent();
            progress1.IsActive = true;
            progress1.Visibility = Visibility.Visible;
            this.Loaded += ExtendedSplash_Loaded;
            DismissExtendedSplash();

            // Listen for window resize events to reposition the extended splash screen image accordingly.  
            // This is important to ensure that the extended splash screen is formatted properly in response to snapping, unsnapping, rotation, etc...  
            Window.Current.SizeChanged += new WindowSizeChangedEventHandler(ExtendedSplash_OnResize);

            ScaleFactor = (double)DisplayInformation.GetForCurrentView().ResolutionScale / 100;

            splash = splashscreen;

            if (splash != null)
            {
                // Register an event handler to be executed when the splash screen has been dismissed.  
                splash.Dismissed += new TypedEventHandler<SplashScreen, Object>(DismissedEventHandler);

                // Retrieve the window coordinates of the splash screen image.  
                splashImageRect = splash.ImageLocation;
            }

            // Restore the saved session state if necessary  
            RestoreStateAsync(loadState);
        }

        async void RestoreStateAsync(bool loadState)
        {
            if (loadState)
                await SuspensionManager.RestoreAsync();
        }

        void ExtendedSplash_OnResize(Object sender, WindowSizeChangedEventArgs e)
        {
            // Safely update the extended splash screen image coordinates. This function will be fired in response to snapping, unsnapping, rotation, etc...  
            if (splash != null)
            {
                // Update the coordinates of the splash screen image.  
                splashImageRect = splash.ImageLocation;
            }
        }

        // Include code to be executed when the system has transitioned from the splash screen to the extended splash screen (application's first view).  
        void DismissedEventHandler(SplashScreen sender, object e)
        {
            dismissed = true;
        }

        async void DismissExtendedSplash()
        {
            await Task.Delay(TimeSpan.FromSeconds(5)); // set your desired delay  
            rootFrame = new Frame();
            LoginMain mainPage = new LoginMain();
            rootFrame.Content = mainPage;
            Window.Current.Content = rootFrame;
            rootFrame.Navigate(typeof(LoginMain)); // call MainPage  
        }

        private void ExtendedSplash_Loaded(object sender, RoutedEventArgs e)
        {
            LogoSvg.Source = new SvgImageSource(new Uri("ms-appx:///Assets/SharedResources/Logo.svg"));
        }

        protected override void OnNavigatingFrom(NavigatingCancelEventArgs e)
        {

        }
    }
}
