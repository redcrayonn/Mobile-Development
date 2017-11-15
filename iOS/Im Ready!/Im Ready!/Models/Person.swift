//
//  Person.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Person : EntityModel {
    let name: String
    var role: Roles
    
    init(id: String, name: String, role: Roles) {
        self.name = name
        self.role = role
        
        super.init(id: id)
    }
    
    enum Roles {
        case admin
        case client
        case caregiver
        case family
    }
}
