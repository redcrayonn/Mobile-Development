using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Data;

namespace ImReady.Helpers.Converters
{
    public class ActivityStatusDoneToVisibilityConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, string culture)
        {
            if (value is int)
            {
                ActivityStatus status = (ActivityStatus)value;
                switch(status)
                {
                    case ActivityStatus.DONE:
                        return Visibility.Collapsed;
                    case ActivityStatus.PENDING:
                        return Visibility.Collapsed;
                    default:
                        return Visibility.Visible;
                }
            }
            else
                return Visibility.Visible;
        }

        public object ConvertBack(object value, Type targetType,
            object parameter, string culture)
        {
            throw new NotImplementedException();
        }
    }
}
