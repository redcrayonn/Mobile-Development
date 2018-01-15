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
    var selectedDate: Date = Date()
    var datesWithEvents: [String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.calendar.select(Date())
        
        // For UITest
        self.calendar.accessibilityIdentifier = "calendar"
        
        appointmentService.getAppointments(forClient: CurrentUser.instance.id!,
                                           onSuccess: { (results) in
                                            self.appointments = results
                                            self.tableView.reloadData()
        }) {
            simpleAlert(atVC: self,
                        withTitle: "Er is iets fout gegaan",
                        andMessage: "Kon afspraken niet ophalen")
        }
    }
    
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date, at monthPosition: FSCalendarMonthPosition) {
        print("did select date \(self.dateFormatter.string(from: date))")
        let selectedDates = calendar.selectedDates.map({self.dateFormatter.string(from: $0)})
        print("selected dates is \(selectedDates)")
        if monthPosition == .next || monthPosition == .previous {
            calendar.setCurrentPage(date, animated: true)
        }
    }
    
    func calendar(_ calendar: FSCalendar, numberOfEventsFor date: Date) -> Int {
        let dateString = self.dateFormatter2.string(from: date)
        if self.datesWithEvents.contains(dateString) {
            return 1
        }
        
        return 0

    }
    
    func calendarCurrentPageDidChange(_ calendar: FSCalendar) {
        print("\(self.dateFormatter.string(from: calendar.currentPage))")
    }

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return appointments.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "calendarCell", for: indexPath) as! CalendarTableViewCell
        
        addEventToCalendar(fromDateTime: appointments[indexPath.row].startDate!)
        
        let startTime = convertToTime(fromDateTime: appointments[indexPath.row].startDate!)
        let endTime = convertToTime(fromDateTime: appointments[indexPath.row].endDate!)
        
        cell.titleLbl.text = appointments[indexPath.row].title
        cell.remarkLbl.text = appointments[indexPath.row].remark
        cell.timeFrameLbl.text = "\(startTime) - \(endTime)"

        return cell
    }
    
    /// Convert the DateTime from the API to a representable time
    func convertToTime(fromDateTime datetime: String) -> String {
        
        // Convert to a Date() object
        let datetimeObject = convertToDateObject(fromDateString: datetime)
        
        let minutes: String = prependZeroIfNeeded(forDateOrTimeUnit: datetimeObject.minute)
        let hours: String = prependZeroIfNeeded(forDateOrTimeUnit: datetimeObject.hour)
        
        print("\(hours):\(minutes)")
        
        return "\(hours):\(minutes)"
    }
    
    /// Convert the DateTime from the API to a representable date to add to the calendar
    func addEventToCalendar(fromDateTime datetime: String) {
        let dateTimeInIsoFormat = convertToDateObject(fromDateString: datetime)
        
        let month = prependZeroIfNeeded(forDateOrTimeUnit: dateTimeInIsoFormat.month)
        let day = prependZeroIfNeeded(forDateOrTimeUnit: dateTimeInIsoFormat.day)
        
        print("\(dateTimeInIsoFormat.year)-\(month)-\(day)")
        let dateString = "\(dateTimeInIsoFormat.year)-\(month)-\(day)"
        
        datesWithEvents.append(dateString)
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
    
    /// Prepend a zero, otherwise
    func prependZeroIfNeeded(forDateOrTimeUnit: Int) -> String {
        let unit: String = String(forDateOrTimeUnit)

        // If the unit only has 1 digit, prepend a zero
        if "\(unit)".count == 1 {
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
