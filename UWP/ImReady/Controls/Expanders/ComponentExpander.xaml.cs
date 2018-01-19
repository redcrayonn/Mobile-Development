using ImReady.Models;
using ImReady.Repositories;
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
    public sealed partial class ComponentExpander : UserControl, INotifyPropertyChanged
    {
        public ComponentExpander()
        {
            this.InitializeComponent();
        }

        public Component Component
        {
            get { return (Component)GetValue(BuildingBlockComponentProperty); }
            set { SetValue(BuildingBlockComponentProperty, value); UpdateLayout(); }
        }

        public static readonly DependencyProperty BuildingBlockComponentProperty =
            DependencyProperty.Register(
                nameof(Component),
                typeof(Component),
                typeof(ComponentExpander),
                new PropertyMetadata(null, OnComponentChangedCallBack));

        private static void OnComponentChangedCallBack(
        DependencyObject sender, DependencyPropertyChangedEventArgs e)
        {
            ComponentExpander c = sender as ComponentExpander;
            if (c != null)
            {
                c.OnComponentChanged();
            }
        }

        public void OnComponentChanged()
        {
            //Grab related data.
             //Raises INotifyPropertyChanged.PropertyChanged
            OnPropertyChanged("Component");
        }

        //Create the OnPropertyChanged method to raise the event
       void OnPropertyChanged(string name)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null)
            {
                handler(this, new PropertyChangedEventArgs(name));
            }
}

public event PropertyChangedEventHandler PropertyChanged;

        private async void SubmitTask_Click(object sender, RoutedEventArgs e)
        {
            ContentDialog confirmDialog = new ContentDialog
            {
                Title = "Uitwerking inleveren",
                Content = "Weet u zeker dat u de uitwerking wilt inleveren?",
                CloseButtonText = "Annuleren",
                PrimaryButtonText = "Inleveren"
            };

            ContentDialogResult result = await confirmDialog.ShowAsync();

            if (result == ContentDialogResult.Primary)
            {

                try
                {
                    var button = sender as Button;
                    var activity = button.DataContext as Activity;
                    var parent = (button.Parent as FrameworkElement) as FrameworkElement;
                    var input = (parent.FindName("TextContent") as TextBox).Text;
                    activity.Content = input;
                    new ActivityService().CompleteActivity(activity);
                    //Update datacontext
                    var updatedComponent = Component;
                    var oldActivity = updatedComponent.Activities.Where(i => i.Id == activity.Id).First();
                    var index = updatedComponent.Activities.ToList().IndexOf(oldActivity);
                    activity.Status = (int)ActivityStatus.PENDING;
                    if (index != -1)
                        updatedComponent.Activities[index] = activity;
                    Component = updatedComponent;
                    
                    FuturePlanRepo.CachedFuturePlan = await new FuturePlanService().GetFuturePlan();
                }
                catch
                {
                    if (Config.GlobalConfig.ShowDebug)
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
        }

        public void UpdateUI()
        {
            
        }
    }
}
