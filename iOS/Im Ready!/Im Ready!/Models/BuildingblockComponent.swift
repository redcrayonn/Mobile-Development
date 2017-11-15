//
//  BuildingblockComponent.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class BuildingblockComponent {
    let id: String!
    let name: String!
    var tasks: [Int]
    
    init(id: String, name: String, tasks: [Int]) {
        self.id = id
        self.name = name
        self.tasks = tasks
    }
}
