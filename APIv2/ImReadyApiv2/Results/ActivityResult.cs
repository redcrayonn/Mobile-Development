using ImReady.Data.Models;

namespace ImReadyApiv2.Results
{
    public class ActivityResult : BaseResult
    {
        public ActivityResult(Activity activity) : base(activity)
        {
            this.Name = activity.Name;
            this.Description = activity.Description;
            this.Points = activity.Points;
        }

        public string Name { get; }
        public string Description { get; }
        public int Points { get; }
    }
}