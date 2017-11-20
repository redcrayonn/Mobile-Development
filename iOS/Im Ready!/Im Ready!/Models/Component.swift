//
//  BuildingblockComponent.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

struct Component : Decodable {
    let id : String?
    let name : String?
    let activities : [Activities]?
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case name = "name"
        case activities = "activities"
    }
}

