using ImReady.Models;
using ImReady.Services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
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
using Windows.UI.Xaml.Navigation;

// The User Control item template is documented at https://go.microsoft.com/fwlink/?LinkId=234236

namespace ImReady.Controls
{
    public sealed partial class ComponentExpander : UserControl
    {
        public ComponentExpander()
        {
            this.InitializeComponent();
        }

        public Component BuildingBlockComponent
        {
            get { return (Component)GetValue(BuildingBlockComponentProperty); }
            set { SetValue(BuildingBlockComponentProperty, value); }
        }

        public static readonly DependencyProperty BuildingBlockComponentProperty =
            DependencyProperty.Register(
                nameof(BuildingBlockComponent),
                typeof(Component),
                typeof(ComponentExpander),
                new PropertyMetadata(null));

        private void SubmitTask_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                var button = sender as Button;
                var activity = button.DataContext as Activity;
                var parent = (button.Parent as StackPanel).Parent as StackPanel;
                var input = (parent.FindName("TextContent") as TextBox).Text;
                activity.Content = input;
                new ActivityService().CompleteActivity(activity);
            }
            catch
            {
                if(Config.GlobalConfig.ShowDebug)
                {
                    MessageDialog msg = new MessageDialog($"Er is iets misgegaan: waarschijnlijk is de layout van de expander control gewijzigd.");
                    msg.ShowAsync();
                }
                else
                {
                    MessageDialog msg = new MessageDialog($"Er is iets misgegaan bij het versturen van de input, probeer het later nogmaals.");
                    msg.ShowAsync();
                }
            }
        }

        public void UpdateUI()
        {
            
        }
    }
}
