using ImReady.Data.Models;

namespace ImReadyApiv2.Results
{
    public class UsefulLinkResult
    {
        public UsefulLinkResult(UsefulLink link)
        {
            this.Url = link.Url;
        }

        public string Url { get; }
    }
}