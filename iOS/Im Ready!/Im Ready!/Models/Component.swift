//
//  BuildingblockComponent.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public struct Component : Decodable {
    let id : String?
    let name : String?
//    let status: String?
    let activities : [Activity]?
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case name = "name"
//        case status = "status"
        case activities = "activities"
    }
    
    enum status {
        case complete
        case pending
        case ongoing
        case irrelevant
    }
}

