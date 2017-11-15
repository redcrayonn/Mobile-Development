//
//  Buildingblock.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Buildingblock {
    let id: String!
    let name: String!
    var Components: [Int] = []
    
    init(id: String, name: String, Components: [Int]) {
        self.id = id
        self.name = name
        self.Components = Components
    }
}
