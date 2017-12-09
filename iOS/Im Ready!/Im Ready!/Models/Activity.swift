//
//  BuildingblockComponentTask.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public struct Activity : Decodable {
    let componentId : String?
    let name : String?
    let description : String?
    let points : Int?
    
    enum CodingKeys: String, CodingKey {
        case componentId = "componentId"
        case name = "name"
        case description = "description"
        case points = "points"
    }    
}

