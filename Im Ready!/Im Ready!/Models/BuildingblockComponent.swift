//
//  BuildingblockComponent.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class BuildingblockComponent {
    let id: Int!
    let name: String!
    var tasks: [Int]
    
    init(id: Int, name: String, tasks: [Int]) {
        self.id = id
        self.name = name
        self.tasks = tasks
    }
}
