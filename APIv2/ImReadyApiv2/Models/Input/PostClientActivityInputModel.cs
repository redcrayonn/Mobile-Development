using ImReady.Data.Enums;
using ImReady.Data.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input
{
    public class PostClientActivityInputModel
    {
        public Status Status { get; set; }

        public string Content { get; set; }

        internal ClientActivity getModel()
        {
            return new ClientActivity()
            {
                Content = Content,
                Status = Status,
            };
        }
    }
}