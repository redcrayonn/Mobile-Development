//
//  BuildingblockResults.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 20/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class BuildingblockResult : Decodable {
    var buildingblocks: [Buildingblock]?
    
    enum CodingKeys: String, CodingKey {
        case buildingblocks
    }
    
//    required init (from decoder: Decoder) throws {
//        let container =  try decoder.container (keyedBy: CodingKeys.self)
//        buildingblocks = try container.decode ([Any].self, forKey: .buildingblocks)
//    }

}
