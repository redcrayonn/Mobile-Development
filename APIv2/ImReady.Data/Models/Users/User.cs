using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity.EntityFramework;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace ImReady.Data.Models.Users
{
    [Table("User")]
    public abstract class User : IdentityUser
    {
        public string FirstName { get; set; }

        [Required]
        public string LastName { get; set; }
    }
}
