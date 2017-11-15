//
//  Relative.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Relative : Person {
    
    override init(id: String, name: String, role: Roles) {
        super.init(id: id, name: name, role: Roles.family)
    }    
}
