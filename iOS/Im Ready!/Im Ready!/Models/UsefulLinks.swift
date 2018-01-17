//
//  UsefulLinks.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class UsefulLinks : EntityModel, Decodable {
    var componentId : String?
    var url : String?
    
    enum CodingKeys: String, CodingKey {
        case componentId
        case url
    }
    
    required init(from decoder: Decoder) throws {
        super.init()
        let values = try decoder.container(keyedBy: CodingKeys.self)
        componentId = try values.decodeIfPresent(String.self, forKey: .componentId)
        url = try values.decodeIfPresent(String.self, forKey: .url)        
    }
}
