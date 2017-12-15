using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
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

		[Required]
        public string Title { get; set; }

		[Required]
		public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

		public string Location { get; set; }

		public string Remark { get; set; }

		public string RelatedCalendarId { get; set; }

		[ForeignKey("RelatedCalendarId")]
		public virtual Calendar RelatedCalendar { get; set; }
    }
}
