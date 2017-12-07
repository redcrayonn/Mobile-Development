using System.ComponentModel.DataAnnotations.Schema;

namespace ImReady.Data.Models
{
    [Table("UsefulLink")]
    public class UsefulLink : EntityModel
    {
        public string Url { get; set; }
    }
}