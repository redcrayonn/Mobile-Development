using ImReady.Data.Models.Users;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ImReadyApiv2.Models.Input
{
    public class PostUserInputModel
    {
        [Required]
        [DataType(DataType.EmailAddress)]
        [EmailAddress]
        public string Email { get; set; }
        [Required]
        [DataType(DataType.Password)]
        public string Password { get; set; }
        [Required]
        public string FirstName { get; set; }
        [Required]
        public string LastName { get; set; }

        internal T GetUser<T>() where T : User, new()
        {
            var user = new T
            {
                UserName = Email,
                Email = Email,
                FirstName = FirstName,
                LastName = LastName
            };
            return user;
        }
    }
}