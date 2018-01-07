using ImReady.Data.Models;

namespace ImReadyApiv2.Results
{
    public class UsefulLinkResult
    {
        public UsefulLinkResult(UsefulLink link)
        {
            this.Id = link.Id;
            this.Url = link.Url;
        }

        public string Id { get; }
        public string Url { get; }
    }
}