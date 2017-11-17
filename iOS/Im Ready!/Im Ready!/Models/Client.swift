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
    var clientRole: Roles = Roles.client
    
    var family: [Family] = []
    var caregivers: [Caregiver] = []
    var buildingblocks: [Buildingblock] = []
    
    //Override the generic role
    override var role: Roles {
        get {
            return clientRole
        }
    }
    
    init(id: String, name: String, points: Int){
        self.points = points
    }
}
