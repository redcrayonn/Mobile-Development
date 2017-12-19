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
}
