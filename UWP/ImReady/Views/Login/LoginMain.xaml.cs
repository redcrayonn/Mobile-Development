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
using Windows.UI.Popups;
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
        private string emptyPassword = String.Empty;
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
                //loginCredential.RetrievePassword();
                LoginName.Text = loginCredential.UserName;
                //HandleLogin(loginCredential.UserName, loginCredential.Password);
            }

            ShowLoginUI();
        }

        private async void HandleLogin(string username, string password)
        {
            //Show loading spinner
            LoginResult loginResult = await userService.Login(username, password);
            //Hide loading spinner

            if(loginResult != null && loginResult.IsValid())
            {
                //Set logged in user
                CurrentUser.SingleInstance.AccessToken = loginResult.access_token;
                CurrentUser.SingleInstance.Username = loginResult.UserName;

                //TODO: add checkbox for saving credentials 
                //Store username and password for future logins
                var vault = new Windows.Security.Credentials.PasswordVault();
                vault.Add(new Windows.Security.Credentials.PasswordCredential(
                    resourceName, username, password));

                //Navigate to Home
                Frame.Navigate(typeof(Home.HomeMain));
            }
            else
            {
                //Render errors.
                //Make sure UI is visible.
                ShowLoginUI();
                MessageDialog message;
                if (string.IsNullOrWhiteSpace(username) || string.IsNullOrWhiteSpace(password))
                {
                    message = new MessageDialog("Er is iets fout gegaan tijdens het inloggen, zorg dat u een geldig gebruikersnaam en wachtwoord ingevuld hebt.");
                }
                else if(loginResult == null)
                {
                    message = new MessageDialog("Er is iets fout gegaan tijdens het inloggen, controleer uw internet connectie en probeer het nogmaals.");
                }
                else
                {
                    message = new MessageDialog("Er is iets fout gegaan tijdens het inloggen, probeer het later nogmaals of neem contact op met uw begeleider en/of administrator");
                }
                await message.ShowAsync();
            }
        }

        private void LoginSubmit_Click(object sender, RoutedEventArgs e)
        {
            string username = LoginName.Text != null ? LoginName.Text : null;
            string password = LoginPassword.Password != null ? LoginPassword.Password : null;

            if (username != null && password != null && !Config.GlobalConfig.MockServices)
                HandleLogin(username, password);
            else if(Config.GlobalConfig.MockServices)
            {
                Frame.Navigate(typeof(Home.HomeMain));
            }
        }

        //TODO: Make optional (checkbox) & sla alles op, nu alleen username / email
        private Windows.Security.Credentials.PasswordCredential GetCredentialFromLocker()
        {
            Windows.Security.Credentials.PasswordCredential credential = null;

            var vault = new Windows.Security.Credentials.PasswordVault();
            if (vault.RetrieveAll().Count > 0)
            {
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
