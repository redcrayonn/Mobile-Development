//
//  Person.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Person : EntityModel {
    var name: String?
    var role: Roles? {
        get {
            return nil
        }
    }
    
    enum Roles {        
        case admin
        case client
        case caregiver
        case family
    }
}
