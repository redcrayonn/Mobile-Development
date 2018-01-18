//
//  Components.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientComponent : Decodable {
    let name : String?
    let description : String?
    let youtubeUrl: String?
    var status : Int?
    let activities : [ClientActivity]?
    let usefulLinks: UsefulLinks
//    var tasks
    let deadline : String?
    let component : Component?
    let id : String?
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case youtubeUrl = "YoutubeURL"
        case status = "Status"
        case activities = "Activities"
        case usefulLinks = "UsefulLinks"
        case deadline = "Deadline"
        case component = "Component"
        case id = "Id"
    }
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        youtubeUrl = try values.decodeIfPresent(String.self, forKey: .youtubeUrl)
        status = try values.decodeIfPresent(Int.self, forKey: .status)
        activities = try values.decodeIfPresent([ClientActivity].self, forKey: .activities)
        usefulLinks = try UsefulLinks(from: decoder)
        deadline = try values.decodeIfPresent(String.self, forKey: .deadline)
        component = try Component(from: decoder)
        id = try values.decodeIfPresent(String.self, forKey: .id)
    }


}
