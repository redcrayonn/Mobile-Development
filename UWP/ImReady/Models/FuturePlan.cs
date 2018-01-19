using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Models
{
    public class FuturePlan
    {
        public string EarnedPoints {
            get
            {
                int points = 0;
                foreach(var block in Blocks)
                {
                    if (block.Components != null)
                    {
                        foreach (var component in block.Components)
                        {
                            if (component.Activities != null)
                            {
                                foreach (var activity in component.Activities)
                                {
                                    if (activity.Status == (int)ActivityStatus.DONE)
                                        points += activity.Points;
                                }
                            }
                        }
                    }
                }
                return points.ToString();
            }
        }
        public BuildingBlock[] Blocks { get; set; }
    }
}
