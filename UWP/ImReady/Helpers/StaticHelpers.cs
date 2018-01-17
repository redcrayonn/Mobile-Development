using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Helpers
{
    public class StaticHelpers
    {
        public static string DeadlineToDaysRemaining(DateTime deadline)
        {
            var daysRemaining = (deadline - DateTime.Now).TotalDays;
            if (daysRemaining < 1)
                return "Vandaag";
            else
                return $"Over {Math.Ceiling((deadline - DateTime.Now).TotalDays)} dagen";
        }
    }
}
