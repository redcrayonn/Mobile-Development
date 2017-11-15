//
//  Client.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Client : Person {
    var points: Int
    var clientRole: Roles
    
    init(id: String, name: String, role: Roles, points: Int) {
        self.points = points
        self.clientRole = Roles.client
        
        super.init(id: id, name: name, role: self.clientRole)
    }
}
