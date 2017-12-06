using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;

namespace ImReady.ViewModels
{
    public class MyItemViewModel
    {
        public ObservableCollection<TextBlock> Items { get; set; } = new ObservableCollection<TextBlock>();

        public string Title { get; set; }

        public MyItemViewModel(string title)
        {
            Title = title;

            Items.Add(new TextBlock() { Text = "SubItem1" });
            Items.Add(new TextBlock() { Text = "SubItem2" });
            Items.Add(new TextBlock() { Text = "SubItem3" });
        }
    }
}
