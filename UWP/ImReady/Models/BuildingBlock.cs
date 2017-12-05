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
        public string Image { get; set; }
        public string Name { get; set; }
        public BuildingBlockType BuildingBlockType { get; set; }
    }

    public enum BuildingBlockType
    {
        Werk,
        Huis,
        Geld,
        Add
    }
}
