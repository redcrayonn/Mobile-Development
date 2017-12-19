//
//  BuildingblockComponent.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Component : EntityModel, Decodable {
    var buildingblockId : String?
    var name : String?
    var description : String?
    var youtubeUrl : String?
    var usefulLinks : [UsefulLinks]?
    var activities : [Activity]?
    
    private enum CodingKeys: String, CodingKey {
        case buildingblockId = "BuildingblockId"
        case name = "Name"
        case description = "Description"
        case youtubeUrl = "YoutubeURL"
        case usefulLinks = "UsefulLink"
        case activities
    }
    
//    init (id: String, buildingblockId: String, name: String, description: String, youtubeUrl : String) {
//        super.init()
//        self.id = id
//        self.buildingblockId = buildingblockId
//        self.name = name
//        self.description = description
//        self.youtubeUrl = youtubeUrl
//    }
}
