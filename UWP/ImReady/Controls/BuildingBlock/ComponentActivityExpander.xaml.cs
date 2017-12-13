using ImReady.Models;
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
using Windows.UI.Xaml.Navigation;

// The User Control item template is documented at https://go.microsoft.com/fwlink/?LinkId=234236

namespace ImReady.Controls
{
    public sealed partial class ComponentActivityExpander : UserControl
    {
        public ComponentActivityExpander()
        {
            this.InitializeComponent();
        }

        public Activity Activity
        {
            get { return (Activity)GetValue(ActivityProperty); }
            set { SetValue(ActivityProperty, value); }
        }

        public static readonly DependencyProperty ActivityProperty =
            DependencyProperty.Register(
                nameof(Activity),
                typeof(Activity),
                typeof(ComponentActivityExpander),
                new PropertyMetadata(null));
    }
}
