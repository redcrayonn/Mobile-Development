using ImReady.Models;
using ImReady.Models.Results;
using ImReady.Services.Web;
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
        private string resourceName = "ImReady";
        private string defaultUserName;

        private UserWebService userService = UserWebService.SingleInstance;

        public LoginMain()
        {
            this.InitializeComponent();
        }

        private void LoadImages()
        {
            LogoSvg.Source = new SvgImageSource(new Uri("ms-appx:///Assets/SharedResources/Logo.svg"));
        }

        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            HideLoginUI();

            LoadImages();

            var loginCredential = GetCredentialFromLocker();

            if (loginCredential != null)
            {
                // There is a credential stored in the locker.
                // Populate the Password property of the credential
                // for automatic login.
                loginCredential.RetrievePassword();
                HandleLogin(loginCredential.UserName, loginCredential.Password);
            }
            else
            {
                // There is no credential stored in the locker.
                // Display UI to get user credentials.
                ShowLoginUI();
            }
        }

        private async void HandleLogin(string username, string password)
        {
            //Show loading spinner
            LoginResult loginResult = await userService.Login(username, password);
            //Hide loading spinner

            if(loginResult.IsValid())
            {
                //Set logged in user
                CurrentUser.SingleInstance.AuthToken = loginResult.AuthToken;
                CurrentUser.SingleInstance.Username = loginResult.UserName;

                //Navigate to Home
                Frame.Navigate(typeof(Home.HomeMain));
            }
            else
            {
                //Render errors.
                //Make sure UI is visible.
                ShowLoginUI();
            }
        }

        private void LoginSubmit_Click(object sender, RoutedEventArgs e)
        {
            string username = LoginName.Text != null ? LoginName.Text : null;
            string password = LoginPassword.Password != null ? LoginName.Text : null;

            if (username != null && password != null)
                HandleLogin(username, password);
        }

        //TODO: Make optional (checkbox)
        private Windows.Security.Credentials.PasswordCredential GetCredentialFromLocker()
        {
            Windows.Security.Credentials.PasswordCredential credential = null;

            var vault = new Windows.Security.Credentials.PasswordVault();
            var credentialList = vault.FindAllByResource(resourceName);
            if (credentialList.Count > 0)
            {
                if (credentialList.Count == 1)
                {
                    credential = credentialList[0];
                }
                else
                    throw new NotImplementedException();
            }

            return credential;
        }
        private void HideLoginUI()
        {
            LoginUIGrid.Visibility = Visibility.Collapsed;
        }

        private void ShowLoginUI()
        {
            LoginUIGrid.Visibility = Visibility.Visible;
        }
    }
}
