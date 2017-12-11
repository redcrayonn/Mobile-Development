using ImReady.Data.Models;
using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Results
{
    public abstract class BaseResult
    {
        public string Id { get; set; }

        public BaseResult(EntityModel entity)
        {
            Id = entity.Id.ToString();
        }

        public BaseResult(User user)
        {
            Id = user.Id.ToString();
        }
    }
}