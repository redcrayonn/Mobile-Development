//
//  Feedback.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 08/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Feedback : EntityModel {
    var caregiverId : String?
    var clientActivityId : String?
    var content : String?
    
    enum CodingKeys: String, CodingKey {
        case caregiverId = "caregiverId"
        case clientActivityId = "clientActivityId"
        case content = "content"
    }    
}
