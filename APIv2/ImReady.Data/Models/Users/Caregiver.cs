using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models.Users
{
    [Table("Caregiver")]
    public class Caregiver : User
    {
        public virtual ICollection<Client> Clients { get; set; }
    }
}
