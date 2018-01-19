//
//  ConvertDatetime.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import Foundation

class ConvertDatetime {
    enum DateOrTime : String {
        case date
        case time
    }
    
    /// Convert the DateTime from the API to a representable time
    func toTimeOrDateString(fromDateTime datetime: String, convertTo: DateOrTime) -> String {
        
        // Convert to a Date() object
        let datetimeObject = toDateObject(fromDateString: datetime)
        
        if convertTo == .time {
            let minutes: String = prependZeroIfNeeded(forDateOrTimeUnit: datetimeObject.minute)
            let hours: String = prependZeroIfNeeded(forDateOrTimeUnit: datetimeObject.hour)
            
            return "\(hours):\(minutes)"
            
        } else if convertTo == .date {
            let year = datetimeObject.year
            let month = prependZeroIfNeeded(forDateOrTimeUnit: datetimeObject.month)
            let day = prependZeroIfNeeded(forDateOrTimeUnit: datetimeObject.day)
            
            return "\(year)-\(month)-\(day)"
        }
        
        return "Could not convert to time or date"
    }
    
    /// Convert the (ISO8601) datetime string to a Date object
    func toDateObject(fromDateString dateString: String) -> Date {
        // Remove the milliseconds from the string
        let pattern = "\\.[0-9]*"
        let regex = try! NSRegularExpression(pattern: pattern, options: [])
        let correctedDateTime = regex.stringByReplacingMatches(
            in: dateString,
            options: [],
            range: NSMakeRange(0, dateString.count),
            withTemplate: "")
        
        return "\(correctedDateTime)+0100".dateInISO8601Format()!
    }
    
    /// Some converted values have only 1 digit, when I want them to have 2
    private func prependZeroIfNeeded(forDateOrTimeUnit: Int) -> String {
        let unit: String = String(forDateOrTimeUnit)
        
        // If the unit only has 1 digit, prepend a zero
        if unit.count == 1 {
            return "0\(forDateOrTimeUnit)"
        }
        
        return unit
    }
}
