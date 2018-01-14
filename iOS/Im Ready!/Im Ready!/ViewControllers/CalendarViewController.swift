//
//  CalendarViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit
import Timepiece

class CalendarViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var tableView: UITableView!
    
    var appointments: [Appointment] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
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
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return appointments.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "calendarCell", for: indexPath) as! CalendarTableViewCell
        
        let startTime = convertToTime(datetime: appointments[indexPath.row].startDate!)
        let endTime = convertToTime(datetime: appointments[indexPath.row].endDate!)
        
        cell.titleLbl.text = appointments[indexPath.row].title
        cell.remarkLbl.text = appointments[indexPath.row].remark
        cell.timeFrameLbl.text = "\(startTime) - \(endTime)"

        return cell
    }
    
    /// Convert the DateTime from the API to a representable time
    func convertToTime(datetime: String) -> String {
        
        // Remove the milliseconds from the string
        let pattern = "\\.[0-9]{3}"
        let regex = try! NSRegularExpression(pattern: pattern, options: [])
        let correctedDateTime = regex.stringByReplacingMatches(
            in: datetime,
            options: [],
            range: NSMakeRange(0, datetime.count),
            withTemplate: "")
        
        // Convert to a Date() object
        let dateTimeInIsoFormat = "\(correctedDateTime)+0100".dateInISO8601Format()!
        
        // Create "00" instead of a single 0
        var minutes: String = String(dateTimeInIsoFormat.minute)
        if dateTimeInIsoFormat.minute == 0 {
            minutes = "00"
        }
        
        return "\(dateTimeInIsoFormat.hour):\(minutes)"
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
