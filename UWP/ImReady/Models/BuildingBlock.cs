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
        public string ComponentCount => $"{Components.Count} onderdelen";

        private BitmapImage image { get; set; }
        public BitmapImage Image
        {
            get
            {
                if(image == null)
                {
                    return GetBuildingBlockImage();
                }
                else
                    return image;
            }
            set
            {
                image = value;
            }
        }

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

        public BitmapImage GetBuildingBlockImage()
        {
            switch(GetBlockType())
            {
                case BuildingBlockType.Wonen:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_home_white_48dp.png") };
                case BuildingBlockType.Verzekering:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_verified_user_white_48dp.png") };
                case BuildingBlockType.Financien:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_euro_symbol_white_48dp.png") };
                case BuildingBlockType.Gezondheid:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_directions_run_white_48dp.png") };
                case BuildingBlockType.Sociaal:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_people_white_48dp.png") };
                case BuildingBlockType.Werk:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_location_city_white_48dp.png") };
                case BuildingBlockType.Studie:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_school_white_48dp.png") };
                case BuildingBlockType.Gezin:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_favorite_white_48dp.png") };
                case BuildingBlockType.RechtenEnPlichten:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_gavel_white_48dp.png") };
                case BuildingBlockType.BehandelPlan:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_assignment_white_48dp.png") };
                case BuildingBlockType.Add:
                    return new BitmapImage() { UriSource = new Uri("ms-appx:///Assets/Material/Blocks/ic_add_white_48dp.png") };
                default:
                    return null;
            }
        }
    }

    public enum BuildingBlockType
    {
        Wonen,
        Verzekering,
        Financien,
        Gezondheid,
        Sociaal,
        Werk,
        Studie,
        Gezin,
        RechtenEnPlichten,
        BehandelPlan,
        Add
    }
}
