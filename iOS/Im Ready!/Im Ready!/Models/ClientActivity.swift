//
//  ClientActivity.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientActivity : EntityModel {
    var clientComponentId: String?
    var status: Status?
    var content: String?
    var feedback: [Feedback]?
    
    enum CodingKeys: String, CodingKey {
        case clienCcomponentId = "clientComponentId"
        case status = "status"
        case content = "content"
        case feedback = "feedback"
    }
}
