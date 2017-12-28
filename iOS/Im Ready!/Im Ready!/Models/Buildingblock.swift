//
//  Buildingblock.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Buildingblock : EntityModel, Decodable {
    var name : String?
    var description: String?
    var type : BlockType?
    var components: [Component]?
    
    enum CodingKeys: String, CodingKey {
        case id = "Id"
        case name = "Name"
        case description = "Description"
        case type = "Type"
        case components = "Components"
    }
    
    required init(from decoder: Decoder) throws {
        super.init()
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        type = try values.decodeIfPresent(Int.self, forKey: .type).map { BlockType(rawValue: $0)! }
        components = try values.decodeIfPresent([Component].self, forKey: .components)
        self.id = try values.decodeIfPresent(String.self, forKey: .id)
    }
}
