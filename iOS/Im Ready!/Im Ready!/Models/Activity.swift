//
//  BuildingblockComponentTask.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Activity : Decodable {
    let name : String? = nil
    let description : String? = nil
    let points : Int? = nil
    let id : String? = nil
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case points = "Points"
        case id = "Id"
    }
    
    init () {}
}

