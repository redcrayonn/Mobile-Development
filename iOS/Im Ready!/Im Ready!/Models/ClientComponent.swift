//
//  Components.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientComponent : Decodable {
    var name : String? = nil
    var description : String? = nil
    var status : Int? = nil
    var activities : [ClientActivities]? = nil
    var deadline : String? = nil
    var component : Component? = nil
    var id : String? = nil
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case status = "Status"
        case activities = "Activities"
        case deadline = "Deadline"
        case component = "Component"
        case id = "Id"
    }
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        status = try values.decodeIfPresent(Int.self, forKey: .status)
        activities = try values.decodeIfPresent([ClientActivities].self, forKey: .activities)
        deadline = try values.decodeIfPresent(String.self, forKey: .deadline)
        component = try Component(from: decoder)
        id = try values.decodeIfPresent(String.self, forKey: .id)
    }


}
