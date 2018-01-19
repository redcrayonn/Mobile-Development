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
    public class MessageTemplateSelector : DataTemplateSelector
    {
        public DataTemplate MessageReceivedTemplate { get; set; }
        public DataTemplate MessageSentTemplate { get; set; }

        protected override DataTemplate SelectTemplateCore(object item, DependencyObject container)
        {
            var message = item as Message;
            var userId = CurrentUser.SingleInstance.Id;

            if (message.SenderId == userId)
                return MessageSentTemplate;
            else
                return MessageReceivedTemplate;
        }
    }
}
