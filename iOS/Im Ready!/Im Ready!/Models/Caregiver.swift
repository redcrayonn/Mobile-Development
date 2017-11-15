//
//  Caregiver.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Caregiver : Person {
    var cargiverRole: Roles
    
    override init(id: String, name: String, role: Roles) {
        self.cargiverRole = Roles.caregiver
        
        super.init(id: id, name: name, role: self.cargiverRole)
    }
    
}
