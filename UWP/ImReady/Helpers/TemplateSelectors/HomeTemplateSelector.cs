using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;

namespace ImReady.Helpers.TemplateSelectors
{
    public class HomeTemplateSelector : DataTemplateSelector
    {
        public DataTemplate DefaultBlockTemplate { get; set; }
        public DataTemplate AddBlockTemplate { get; set; }

        protected override DataTemplate SelectTemplateCore(object item, DependencyObject container)
        {
            var dataItem = item as BuildingBlock;

            if(dataItem.GetBlockType() == BuildingBlockType.Add)
            {
                return AddBlockTemplate;
            }

            return DefaultBlockTemplate;
        }
    }
}
