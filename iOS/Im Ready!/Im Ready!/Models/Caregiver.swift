//
//  Caregiver.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Caregiver : Person {
    var caregiverRole: Roles = Roles.caregiver
    
    //Override the generic role
    override var role: Roles {
        get {
            return caregiverRole
        }
    }
    
    init(id: String, name: String){

    }
}
