//
//  FutureplanResult.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class FutureplanResult : Decodable {
    var buildingblocks: [ClientBuildingblock]?
    
    enum CodingKeys: String, CodingKey {
        case buildingblocks = "Blocks"
    }
}
