using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
    public abstract class EntityModel
    {
        [Key]
        public string Id { get; set; }

        public bool Deleted { get; set; }
    }
}
