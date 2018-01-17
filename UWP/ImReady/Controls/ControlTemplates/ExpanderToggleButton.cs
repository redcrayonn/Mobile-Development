using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Documents;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;

// The Templated Control item template is documented at https://go.microsoft.com/fwlink/?LinkId=234235

namespace ImReady.Controls.ControlTemplates
{
    public sealed class ExpanderToggleButton : ToggleButton, INotifyPropertyChanged
    {
        public ExpanderToggleButton()
        {
            this.DefaultStyleKey = typeof(ExpanderToggleButton);
            DataContext = this;
        }

        public static DependencyProperty DeadlineProperty =
        DependencyProperty.RegisterAttached("Deadline",
                                            typeof(string),
                                            typeof(ExpanderToggleButton),
                                            new PropertyMetadata(null));

        public event PropertyChangedEventHandler PropertyChanged;

        public static string GetDeadline(DependencyObject target)
        {
            return (string)target.GetValue(DeadlineProperty);
        }
        public static void SetDeadline(DependencyObject target, string value)
        {
            target.SetValue(DeadlineProperty, value);
        }
    }
}
