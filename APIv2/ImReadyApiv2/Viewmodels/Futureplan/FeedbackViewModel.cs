using ImReady.Data.Models;

namespace ImReadyApiv2.Viewmodels
{
    public class FeedbackViewModel : BaseEntityViewModel
    {
        public FeedbackViewModel(Feedback feedback) : base(feedback)
        {
            Content = feedback.Content;
            CaregiverName = feedback.Caregiver.Firstname;
        }
        public string Content { get; set; }

        public string CaregiverName { get; set; }
    }
}