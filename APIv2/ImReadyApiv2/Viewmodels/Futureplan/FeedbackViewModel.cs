using ImReady.Data.Models;

namespace ImReadyApiv2.Results
{
    public class FeedbackViewModel : BaseResult
    {
        public FeedbackViewModel(Feedback feedback) : base(feedback)
        {
            Content = feedback.Content;
            CaregiverName = feedback.Caregiver.FirstName;
        }
        public string Content { get; set; }

        public string CaregiverName { get; set; }
    }
}