//
//  Relative.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Family : Person {
    var familyRole: Roles = Roles.family
    
    //Override the generic role
    override var role: Roles {
        get {
            return familyRole
        }
    }
    
    init(id: String, name: String){
    }
    
}
