using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models.Users
{
    [Table("Relative")]
    public class Relative : User
    {
        public Guid RelativeId { get; set; }

        [ForeignKey("RelativeId")]
        public Relative relative { get; set; }
    }
}
