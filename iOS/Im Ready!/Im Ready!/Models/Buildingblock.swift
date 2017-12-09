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
    var blockType: Blocktype?
    
    enum CodingKeys: String, CodingKey {
        case name = "name"
        case description = "description"
        case blockType = "blockType"
    }
}
