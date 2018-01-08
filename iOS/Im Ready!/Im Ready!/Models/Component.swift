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
        case id = "Id"
        case buildingblockId = "BuildingblockId"
        case name = "Name"
        case description = "Description"
        case youtubeUrl = "YoutubeURL"
        case usefulLinks = "UsefulLink"
        case activities        
    }

    required init(from decoder: Decoder) throws {
        super.init()
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        youtubeUrl = try values.decodeIfPresent(String.self, forKey: .youtubeUrl)
        usefulLinks = try values.decodeIfPresent([UsefulLinks].self, forKey: .usefulLinks)
        activities = try values.decodeIfPresent([Activity].self, forKey: .activities)
        self.id = try values.decodeIfPresent(String.self, forKey: .id)
    }

}
