using ImReady.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;
using Windows.UI.Popups;

namespace ImReady.Services.Web
{
    public static class BaseClient
    {
        private static bool hasError = false;
        public static async Task<T> HandleAsync<T>(string uri, HttpMethod httpMethod = null, Dictionary<string, string> parameters = null, bool EnableAuthentication = false, bool canDelay = false, bool alwaysReturnDefault = false)
        {
            if (httpMethod == null) httpMethod = HttpMethod.Get;
            using (var client = new HttpClient())
            {
                client.BaseAddress = new Uri("http://inhollandbackend.azurewebsites.net/");
                if (EnableAuthentication && CurrentUser.SingleInstance.IsLoggedIn)
                {
                    var token = CurrentUser.SingleInstance.AccessToken;
                    client.DefaultRequestHeaders.Add("x-authtoken", token);

                }
                try
                {
                    if (parameters == null) parameters = new Dictionary<string, string>();
                    using (var content = new FormUrlEncodedContent(parameters))
                    {
                        using (var request = new HttpRequestMessage(httpMethod, uri))
                        {
                            request.Content = content;

                            if (hasError && canDelay)
                            {
                                await Task.Delay(3000);
                            }
                            using (var response = await client.SendAsync(request))
                            {
                                if (response.IsSuccessStatusCode)
                                {
                                    var json = await response.Content.ReadAsStringAsync();
                                    if (alwaysReturnDefault)
                                        return default(T);
                                    var result = JsonConvert.DeserializeObject<T>(json);
                                    return result;
                                }
                                else
                                {
                                    var errorNessage = response.StatusCode == System.Net.HttpStatusCode.Unauthorized ? "Uw account is niet gevonden met de gebruikte wachtwoord en gebruikersnaam. \n Probeer het nogmaals." : response.ReasonPhrase;
                                    CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(CoreDispatcherPriority.Normal,
                                    () =>
                                    {
                                        MessageDialog msg = new MessageDialog($"Er is iets misgegaan: \n {errorNessage}");
                                        msg.ShowAsync();
                                    });
                                    return default(T);
                                }
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    if (!hasError)
                    {
                        void CommandInvokedHandler(IUICommand command)
                        {
                            // Display message showing the label of the command that was invoked
                            hasError = false;
                        }

                        CoreApplication.MainView.CoreWindow.Dispatcher.RunAsync(CoreDispatcherPriority.Normal,
                        () =>
                        {
                            MessageDialog msg = new MessageDialog($"Er is iets misgegaan: \n {e.Message}" + (canDelay ? " volgende poging over 3 seconden..." : ""));
                            msg.Commands.Add(new UICommand("Close", new UICommandInvokedHandler(CommandInvokedHandler)));
                            msg.ShowAsync();
                        });
                    }
                    hasError = true;
                    return default(T);
                }
            }
        }
    }
}
