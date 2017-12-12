using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models.Users
{
    [Table("Client")]
    public class Client : User
    {

        public int Points { get; set; }

        public string CaregiverId { get; set; }

        [ForeignKey("CaregiverId")]
        public virtual Caregiver Caregiver { get; set; }

        public virtual ICollection<ClientBuildingBlock> Blocks {get;set;}
        public virtual ICollection<Relative> Relatives { get; set; }
    }
}
