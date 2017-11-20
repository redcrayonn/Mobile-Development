//
//  BuildingblockComponentTask.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

struct Activities : Decodable {
    let id : String?
    let name : String?
    let description : String?
    let points : Int?
    let assignment : String?
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case name = "name"
        case description = "description"
        case points = "points"
        case assignment = "assignment"
    }    
}

