//
//  CalendarViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit
import Timepiece
import FSCalendar

class CalendarViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, FSCalendarDataSource, FSCalendarDelegate {
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var calendar: FSCalendar!
    
    fileprivate lazy var dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy/MM/dd"
        return formatter
    }()
    
    fileprivate lazy var dateFormatter2: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter
    }()
    
    
    var appointments: [Appointment] = []
    var appointmentsForDate: [Appointment] = []
    var selectedDate: Date = Date.today()
    var datesWithEvents: [String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.calendar.select(Date())
        
        // For UITest
        self.calendar.accessibilityIdentifier = "calendar"
        
        appointmentService.getAppointments(forClient: CurrentUser.instance.id!,
                                           onSuccess: { (results) in
                                            self.appointments = results
                                            self.addEventsToCalendar(fromAppointments: self.appointments)
                                            self.getAppointmentsForDate(fromAppointments: self.appointments)
        }) {
            simpleAlert(atVC: self,
                        withTitle: "Er is iets fout gegaan",
                        andMessage: "Kon afspraken niet ophalen")
        }
        
        
    }
    
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date, at monthPosition: FSCalendarMonthPosition) {
        let selectedDates = calendar.selectedDates.map({self.dateFormatter.string(from: $0)})
        selectedDate = date
        getAppointmentsForDate(fromAppointments: self.appointments)

        if monthPosition == .next || monthPosition == .previous {
            calendar.setCurrentPage(date, animated: true)
        }
    }
    
    func calendar(_ calendar: FSCalendar, numberOfEventsFor date: Date) -> Int {
        
        // Count the number of appointments per day and put it in a dictionary
        let counts = self.datesWithEvents.reduce(into: [:]) { counts, date in
            counts[date, default: 0] += 1
        }
        
        let dateString = self.dateFormatter2.string(from: date)
        if self.datesWithEvents.contains(dateString) {
            return counts[dateString]!
        }
        
        return 0
        
    }
    
    func calendarCurrentPageDidChange(_ calendar: FSCalendar) {
        print("\(self.dateFormatter.string(from: calendar.currentPage))")
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return appointmentsForDate.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "calendarCell", for: indexPath) as! CalendarTableViewCell
        
        print(self.appointmentsForDate)
        print(indexPath.row)
        
        print(self.appointmentsForDate[indexPath.row].startDate!)
        print(self.appointmentsForDate[indexPath.row].endDate!)
        
        let startTime = convertToTimeOrDateString(fromDateTime: appointmentsForDate[indexPath.row].startDate!, convertTo: .time)
        let endTime = convertToTimeOrDateString(fromDateTime: appointmentsForDate[indexPath.row].endDate!, convertTo: .time)
        
        cell.titleLbl.text = appointmentsForDate[indexPath.row].title
        cell.timeFrameLbl.text = "\(startTime) - \(endTime)"
        
        return cell
        
    }
    
    /// Filter the list of appointmets by the selected date of the calendar.
    func getAppointmentsForDate(fromAppointments appointments: [Appointment]) {
        var filteredAppointments: [Appointment] = []
        for appointment in appointments {
            let dateOfAppointment = convertToTimeOrDateString(fromDateTime: appointment.startDate!,
                                                              convertTo: .date).date(inFormat: "yyyy-MM-dd")
            let selectedDate = "\(self.selectedDate.year)-\(self.selectedDate.month)-\(self.selectedDate.day)".date(inFormat: "yyyy-MM-dd")
            
            if dateOfAppointment == selectedDate {
                filteredAppointments.append(appointment)
            }
        }
        
        self.appointmentsForDate = sortListByTime(forList: filteredAppointments)
        self.tableView.reloadData()
    }
    
    /// Sort the list by time.
    func sortListByTime(forList appointments: [Appointment]) -> [Appointment] {
        let sortedList: [Appointment] = appointments.sorted { (a1, a2) -> Bool in
            let a1Time = convertToTimeOrDateString(fromDateTime: a1.startDate!, convertTo: .time)
            let a2Time = convertToTimeOrDateString(fromDateTime: a2.startDate!, convertTo: .time)
            
            if a1Time > a2Time {
                return false
            }
            
            return true
        }
        
        return sortedList
    }
    
    /// Convert the DateTime from the API to a representable time
    func convertToTimeOrDateString(fromDateTime datetime: String, convertTo: DateOrTime) -> String {
        
        // Convert to a Date() object
        let datetimeObject = convertToDateObject(fromDateString: datetime)
        
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
    
    public enum DateOrTime : String {
        case date
        case time
    }
    
    /// Convert the DateTime from the API to a representable date to add as event to the calendar
    func addEventsToCalendar(fromAppointments appointments: [Appointment]) {
        
        for appointment in appointments {
            let datetime = appointment.startDate!
            let dateString = convertToTimeOrDateString(fromDateTime: datetime, convertTo: .date)
            
            datesWithEvents.append(dateString)
        }
        
        self.calendar.reloadData()
    }
    
    /// Convert the datetime string to a Date object
    func convertToDateObject(fromDateString dateString: String) -> Date {
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
    
    /// Prepend a zero if needed
    func prependZeroIfNeeded(forDateOrTimeUnit: Int) -> String {
        let unit: String = String(forDateOrTimeUnit)
        
        // If the unit only has 1 digit, prepend a zero
        if unit.count == 1 {
            return "0\(forDateOrTimeUnit)"
        }
        
        return unit
    }
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
}
