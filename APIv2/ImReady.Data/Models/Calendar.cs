using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ImReady.Data.Models
{
	[Table("Calendar")]
	public class Calendar : EntityModel
    {
		public string UserId { get; set; }

		[ForeignKey("UserId")]
		public virtual User User { get; set; } 

        public string Title { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

		public String Location { get; set; }

		public String Remark { get; set; }
    }
}
