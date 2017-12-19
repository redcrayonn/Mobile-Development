//
//  Buildingblocks.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientBuildingblock : Decodable{
    let name : String?
    let description : String?
    let block : Buildingblock?
    let type : Int?
    let components : [ClientComponent]?
    let id : String?
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case block = "Block"
        case type = "Type"
        case components = "Components"
        case id = "Id"
    }
}
