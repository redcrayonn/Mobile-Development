//
//  Feedback.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Feedback : EntityModel, Decodable {
    var caregiverName : String?
    var clientActivityId : String?
    var content : String?
    var sent: String?
    
    enum CodingKeys: String, CodingKey {
        case caregiverName = "CaregiverName"
        case sent = "Sent"
        case clientActivityId = "clientActivityId"
        case content = "Content"
    }
    
    required init(from decoder: Decoder) throws {
        super.init()
        let values = try decoder.container(keyedBy: CodingKeys.self)
        content = try values.decodeIfPresent(String.self, forKey: .content)
        caregiverName = try values.decodeIfPresent(String.self, forKey: .caregiverName)
        clientActivityId = try values.decodeIfPresent(String.self, forKey: .clientActivityId)
        sent = try values.decodeIfPresent(String.self, forKey: .sent)
    }

}
