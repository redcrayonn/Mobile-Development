//
//  BuildingblockComponentTask.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Task {
    let id: String
    let name: String
    let message: String
    
    init(id: String, name: String, message: String) {
        self.id = id
        self.name = name
        self.message = message
    }
}
