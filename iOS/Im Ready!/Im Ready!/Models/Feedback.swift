//
//  Feedback.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Feedback : EntityModel, Decodable {
    var caregiverId : String?
    var clientActivityId : String?
    var content : String?
    
    enum CodingKeys: String, CodingKey {
        case caregiverId = "caregiverId"
        case clientActivityId = "clientActivityId"
        case content = "content"
    }
    
    required init(from decoder: Decoder) throws {
        super.init()
        let values = try decoder.container(keyedBy: CodingKeys.self)
        content = try values.decodeIfPresent(String.self, forKey: .content)
        caregiverId = try values.decodeIfPresent(String.self, forKey: .caregiverId)
        clientActivityId = try values.decodeIfPresent(String.self, forKey: .clientActivityId)
    }

}
