//
//  AppointmentResult.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import Foundation

class AppointmentResult : Decodable {
    var appointments: [Appointment]?
    
    enum CodingKeys: String, CodingKey {
        case appointments
    }
}
