//
//  ClientTask.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientTask : EntityModel {
    var name : String?
    var deadlineDate : Date?
    var content : String?
    var status : Status?
    var feedback : String?
    
    enum CodingKeys: String, CodingKey {
        case name = "name"
        case deadlineDate = "deadlineDate"
        case content = "content"
        case status = "status"
        case feedback = "feedback"
    }
}
