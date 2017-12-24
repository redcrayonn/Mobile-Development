using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Globalization;
using System.Linq;
using System.Reflection;
using System.Web;

namespace ImReadyApiv2.Attributes {
	public class CompareIfWithinADayAttribute : CompareAttribute{

		public CompareIfWithinADayAttribute (string otherProperty) : base(otherProperty) {
			ErrorMessage = "'{0}' must be equal or greater than '{1}' and today";
		}

		protected override ValidationResult IsValid (object value, ValidationContext validationContext) {
			PropertyInfo otherPropertyInfo = validationContext.ObjectType.GetProperty(OtherProperty);

			if (otherPropertyInfo == null) {
				return new ValidationResult(String.Format(CultureInfo.CurrentCulture, "Could not find a property named {0}", OtherProperty));
			}

			object otherPropertyValue = otherPropertyInfo.GetValue(validationContext.ObjectInstance, null);
			if(typeof(DateTime) != otherPropertyValue.GetType() || typeof(DateTime) != value.GetType()) {
				return new ValidationResult("DateTime is the only valid type for this attribute");
			}

			DateTime otherPropertyValueDate = (DateTime)otherPropertyValue;
			DateTime valueDate = (DateTime)value;

			if (valueDate < otherPropertyValueDate || otherPropertyValueDate.Date != valueDate.Date) {
				return new ValidationResult(FormatErrorMessage(validationContext.DisplayName));
			}

			return null;
		}
	}
}