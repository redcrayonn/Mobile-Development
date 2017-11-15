//
//  Caregiver.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Caregiver : Person {
    
    override init(id: String, name: String, role: Roles) {
        role = Roles.caregiver
        super.init(id: id, name: name, role: Roles.caregiver)
    }
    
}
