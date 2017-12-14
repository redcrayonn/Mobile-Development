//
//  Buildingblock.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Buildingblock :  EntityModel, Decodable {
    var name : String?
    var description: String?
    var blockType: Int?
    var components: [Component]?
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case blockType = "Type"
        case components = "Components"
    }
    
    init(id: String, name: String, description: String, blockType: Int) {
        super.init()
        self.id = id
        self.name = name
        self.description = description
        self.blockType = blockType
    }
}
