using ImReady.Data.Models;

namespace ImReadyApiv2.Results
{
    public class ComponentResult : BaseResult
    {
        public ComponentResult(Component component) : base(component)
        {
            Name = component.Name;
            Description = component.Description;
        }
        public string Name { get; set; }

        public string Description { get; set; }
    }
}