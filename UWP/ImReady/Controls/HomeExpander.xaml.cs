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
using Windows.UI.Xaml.Navigation;

// The User Control item template is documented at https://go.microsoft.com/fwlink/?LinkId=234236

namespace ImReady.Controls
{
    public sealed partial class HomeExpander : UserControl
    {
        public HomeExpander()
        {
            this.InitializeComponent();
        }

        public string ComponentName
        {
            get { return (string)GetValue(ComponentNameProperty); }
            set { SetValue(ComponentNameProperty, value); }
        }

        public static readonly DependencyProperty ComponentNameProperty =
            DependencyProperty.Register(
                nameof(ComponentName),
                typeof(string),
                typeof(HomeExpander),
                new PropertyMetadata(null, PresentationSlidePropertyChanged));

        private static void PresentationSlidePropertyChanged(
            DependencyObject o, DependencyPropertyChangedEventArgs e)
        {
            //((PresentationViewer)o).ShowSlideContent();
        }
    }
}
