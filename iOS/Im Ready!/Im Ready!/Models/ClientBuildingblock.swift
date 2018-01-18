//
//  Buildingblocks.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientBuildingblock : Decodable{
    var name : String?
    var description : String?
    var block : Buildingblock?
    var type : BlockType?
    var components : [ClientComponent]?
    var id : String?
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case block = "Block"
        case type = "Type"
        case components = "Components"
        case id = "Id"
    }
    
    init(type: BlockType, name: String) {
        self.name = name
        self.type = type
    }
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        block = try Buildingblock(from: decoder)
        type = try values.decodeIfPresent(Int.self, forKey: .type).map { BlockType(rawValue: $0)! }
        components = try values.decodeIfPresent([ClientComponent].self, forKey: .components)
        id = try values.decodeIfPresent(String.self, forKey: .id)
    }
}
