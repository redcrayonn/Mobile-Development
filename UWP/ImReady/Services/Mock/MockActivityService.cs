using ImReady.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ImReady.Models;

namespace ImReady.Services.Mock
{
    public class MockActivityService : BaseService, IActivityService
    {
        public Activity GetActivity(int ComponentId)
        {
            return new Activity()
            {
                Name = "Reageer op 5 woningen",
                Deadline = DateTime.MaxValue,
                Description = "Zoek een huisje onder de zon.",
            };
        }
    }
}
