//
//  AppointmentService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import Foundation

public class AppointmentService : Service {
    
    func getAppointments(forClient userId: String,
                         onSuccess: @escaping ([Appointment]) -> (),
                         onFailure: @escaping () -> ()) {
        var appointments: [Appointment] = []
        
        apiClient.send(toRelativePath: "user/\(userId)/calendar",
            withHttpMethod: .get,
            onSuccessParser: { (data) in
                print(data)
                do {
                    let results = try JSONDecoder().decode(
                        [Appointment].self, from: data)
                    
                    for appointment in results {
                        appointments.append(appointment)
                    }
                    onSuccess(appointments)
                    
                } catch {
                    print("Failed to convert JSON")
                    onFailure()
                }
        }) {
            print("failed to fetch appointments for client \(userId)")
            onFailure()
        }
    }
}
