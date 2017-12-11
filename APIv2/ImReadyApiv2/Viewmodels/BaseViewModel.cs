using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Viewmodels
{
    public abstract class BaseEntityViewModel
    {
        public string Id { get; set; }

        public BaseEntityViewModel(EntityModel entity)
        {
            this.Id = entity.Id.ToString();
        }
    }
}