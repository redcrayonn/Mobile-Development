//
//  Buildingblock.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

struct Buildingblock : Decodable {
    let id : String?
    let name : String?
    let description : String?
    let imageURL : String?
    let components : [Component]?
    
    enum CodingKeys: String, CodingKey {        
        case id = "id"
        case name = "name"
        case description = "description"
        case imageURL = "imageURL"
        case components = "components"
    }
}
