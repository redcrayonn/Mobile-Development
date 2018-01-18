using ImReady.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;

namespace ImReady.Helpers.Converters
{
    public class ActivityStatusToImageConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, string culture)
        {
            BitmapImage deadlineImage = new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/ic_query_builder_deep_orange_400_18dp.png")};
            BitmapImage awaitingReviewImage = new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/ic_hourglass_empty_deep_orange_400_18dp.png") };
            BitmapImage doneImage = new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/ic_done_green_600_18dp.png") };

            int statusInt = value != null && value is int ? (int)value : 0;
            ActivityStatus status = (ActivityStatus)statusInt;
            switch (status)
            {
                case ActivityStatus.ONGOING:
                    return deadlineImage;
                case ActivityStatus.PENDING:
                    return awaitingReviewImage;
                case ActivityStatus.DONE:
                    return doneImage;
                default:
                    return string.Empty;
            }

        }

        public object ConvertBack(object value, Type targetType,
            object parameter, string culture)
        {
            throw new NotImplementedException();
        }

    }
}
