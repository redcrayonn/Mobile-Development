//
//  ClientComponent.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientComponent : EntityModel {
    var description : String?
    var clientBuildingblockId : String?
    var status : Status?
    
    enum CodingKeys: String, CodingKey {
        case desciption = "description"
        case clientBuildingblockId = "clientBuildingblockId"
        case status = "status"
    }
}
