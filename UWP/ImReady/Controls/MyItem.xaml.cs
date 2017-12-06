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
    public sealed partial class MyItem : UserControl
    {
        private MyItemState _state;
        public MyItemState State
        {
            get { return _state; }
            set
            {
                _state = value;
                VisualStateManager.GoToState(this, _state.ToString(), true);
            }
        }

        public MyItem()
        {
            InitializeComponent();

            State = MyItemState.Regular;
        }

        private void Title_OnTapped(object sender, TappedRoutedEventArgs e)
        {
            if (State == MyItemState.Regular)
            {
                State = MyItemState.Expanded;
            }
            else
            {
                State = MyItemState.Regular;
            }
        }

        private void Items_OnItemClick(object sender, ItemClickEventArgs e)
        {
            // action after subitem is clicked
        }
    }

    public enum MyItemState
    {
        Regular,
        Expanded
    }
}
