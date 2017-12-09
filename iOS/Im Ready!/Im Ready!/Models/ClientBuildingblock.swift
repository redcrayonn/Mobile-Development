//
//  ClientBuildingblock.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientBuildingblock : EntityModel {
    var buildingblockId : String?
    var clientId : String?
    
    enum CodingKeys: String, CodingKey {
        case buildingblockId = "buildingblockId"
        case clientId = "clientId"
    }
}
