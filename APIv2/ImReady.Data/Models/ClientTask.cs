using ImReady.Data.Enums;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ImReady.Data.Models
{
    [Table("ClientTask")]
    public class ClientTask : EntityModel
    {
		[Required]
        public string Name { get; set; }

		[Required]
		public DateTime DeadlineDate { get; set; }

		[Required]
		public string Description { get; set; }

		[Required]
		public Status Status { get; set; }

        public string Feedback { get; set; }

		public string ClientComponentId { get; set; }

		[ForeignKey("ClientComponentId")]
		public virtual ClientComponent ClientComponent { get; set; }
	}
}