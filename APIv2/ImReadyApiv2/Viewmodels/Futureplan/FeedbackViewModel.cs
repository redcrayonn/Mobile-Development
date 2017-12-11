using ImReady.Data.Models;

namespace ImReadyApiv2.Viewmodels
{
    public class FeedbackViewModel : EntityModel
    {
        public string Content { get; set; }

        public string CaregiverName { get; set; }
    }
}