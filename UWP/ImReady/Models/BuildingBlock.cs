using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Media.Imaging;

namespace ImReady.Models
{
    public class BuildingBlock
    {
        //Custom
        public string Image { get; set; }

        //JSON props below
        public string Name { get; set; }
        public string Description { get; set; }
        public BuildingBlock Block { get; set; }
        public int Type { get; set; }
        public List<Component> Components { get; set; }
        public string Id { get; set; }

        public BuildingBlockType GetBlockType()
        {
            return (BuildingBlockType)this.Type;
        }
    }

    public enum BuildingBlockType
    {
        Werk,
        Huis,
        Geld,
        Add
    }
}
