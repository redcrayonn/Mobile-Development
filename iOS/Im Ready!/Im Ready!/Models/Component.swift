//
//  BuildingblockComponent.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class Component : EntityModel, Decodable {
    var buildingblockId : String?
    var name : String?
    var description : String?
    var youtubeUrl : String?
    var usefulLinks : [UsefulLinks]
    

    enum CodingKeys: String, CodingKey {
        case buildingblockId = "buildingblockId"
        case name = "name"
        case description = "description"
        case youtubeUrl = "youtubeUrl"
        case usefulLinks = "usefulLinks"
    }
}

