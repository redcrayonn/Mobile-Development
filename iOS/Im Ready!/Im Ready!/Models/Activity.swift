//
//  BuildingblockComponentTask.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Activity : EntityModel, Decodable {
    var name : String?
    var description : String?
    var points : Int?
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case points = "Points"
        case id = "Id"
    }
    
    required init(from decoder: Decoder) throws {
        super.init()
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        points = try values.decodeIfPresent(Int.self, forKey: .points)
//        self.id = try values.decodeIfPresent(String.self, forKey: .id)
    }

}

