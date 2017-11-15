//
//  Relative.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Relative : Person {
    var relativeRole: Roles
    override init(id: String, name: String, role: Roles) {
        self.relativeRole = Roles.family
        
        super.init(id: id, name: name, role: self.relativeRole)
    }    
}
