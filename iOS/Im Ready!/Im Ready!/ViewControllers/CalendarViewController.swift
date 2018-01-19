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
import Reachability
import DZNEmptyDataSet
import ChameleonFramework

class CalendarViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, FSCalendarDataSource, FSCalendarDelegate, DZNEmptyDataSetDelegate, DZNEmptyDataSetSource {
    
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
    
    let convertDatetime: ConvertDatetime = ConvertDatetime()
    var appointments: [Appointment] = []
    var appointmentsForDate: [Appointment] = []
    var selectedDate: Date = Date.today()
    var datesWithEvents: [String] = []
    let reachability = Reachability()!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.calendar.select(Date())
        
        // For UITest
        self.calendar.accessibilityIdentifier = "calendar"
        
        getAppointments()
        
        self.tableView.emptyDataSetDelegate = self
        self.tableView.emptyDataSetSource = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        NotificationCenter.default.addObserver(self, selector: #selector(reachabilityChanged), name: .reachabilityChanged, object: reachability)
        do{
            try reachability.startNotifier()
        }catch{
            print("could not start reachability notifier")
        }

    }
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date, at monthPosition: FSCalendarMonthPosition) {
        _ = calendar.selectedDates.map({self.dateFormatter.string(from: $0)})
        self.selectedDate = date
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
        
        // Draw the number of dots per day (max is 3 dots per day)
        let dateString = self.dateFormatter2.string(from: date)
        if self.datesWithEvents.contains(dateString) {
            return counts[dateString]!
        }
        
        return 0
    }
    
    func description(forEmptyDataSet scrollView: UIScrollView!) -> NSAttributedString! {
        let string = "Geen afspraken op deze dag"
        let attribute = [ NSAttributedStringKey.foregroundColor: UIColor.gray ]
        let attributedString = NSAttributedString(string: string, attributes: attribute)
        
        return attributedString
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return appointmentsForDate.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "calendarCell", for: indexPath) as! CalendarTableViewCell
        
        let startTime = convertDatetime.toTimeOrDateString(fromDateTime: appointmentsForDate[indexPath.row].startDate!, convertTo: .time)
        let endTime = convertDatetime.toTimeOrDateString(fromDateTime: appointmentsForDate[indexPath.row].endDate!, convertTo: .time)
        
        cell.titleLbl.text = appointmentsForDate[indexPath.row].title
        cell.timeFrameLbl.text = "\(startTime) - \(endTime)"
        
        return cell
    }
    
    func getAppointments() {
        guard reachability.connection != .none else {
            simpleAlert(atVC: self, withTitle: "Geen internetverbinding", andMessage: "Kan geen afspraken ophalen zonder internetverbinding")
            
            return
        }
        
        // There is a bug/feature in the API where posting a new Appointment results in two Appointments
        // That's why there are duplicates, it's not the fault of this beautiful code
        appointmentService.getAppointments(forClient: CurrentUser.instance.id!,
                                           onSuccess: { (results) in
                                            self.appointments = results
                                            self.addEventsToCalendar(fromAppointments: self.appointments)
                                            self.getAppointmentsForDate(fromAppointments: self.appointments)
                                            
                                            self.tableView.reloadData()
        }) {
            print("Failed to fetch appointments")
            
            simpleAlert(atVC: self,
                        withTitle: "Er is iets fout gegaan",
                        andMessage: "Kon afspraken niet ophalen")
        }
    }
    
    /// Filter the list of appointmets by the selected date of the calendar.
    func getAppointmentsForDate(fromAppointments appointments: [Appointment]) {
        
        var appointmentsOfSelectedDate: [Appointment] = []
        
        for appointment in appointments {
            let dateOfAppointment = convertDatetime.toTimeOrDateString(
                fromDateTime: appointment.startDate!,
                convertTo: .date).date(inFormat: "yyyy-MM-dd")
            
            let userSelectedDate = "\(self.selectedDate.year)-\(self.selectedDate.month)-\(self.selectedDate.day)".date(inFormat: "yyyy-MM-dd")
            
            if dateOfAppointment == userSelectedDate {
                appointmentsOfSelectedDate.append(appointment)
            }
        }
        
        // Sort the list before updating
        self.appointmentsForDate = appointmentsOfSelectedDate.sorted {$0.startDate! < $1.startDate!}
    }
    
    /// Convert the DateTime from the API to a representable date to add as event to the calendar
    func addEventsToCalendar(fromAppointments appointments: [Appointment]) {
        
        for appointment in appointments {
            let dateString = convertDatetime.toTimeOrDateString(fromDateTime: appointment.startDate!, convertTo: .date)
            
            datesWithEvents.append(dateString)
        }
        
        self.calendar.reloadData()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    
    // When the user switches on internet, automatically fetch the appointments again
    @objc func reachabilityChanged(note: Notification) {
        let reachability = note.object as! Reachability
        reachability.whenReachable = { _ in
            self.getAppointments()
        }
    }
}
