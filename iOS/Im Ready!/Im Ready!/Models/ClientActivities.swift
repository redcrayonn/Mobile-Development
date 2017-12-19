//
//  ClientActivities.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

struct ClientActivities : Decodable {
    let name : String?
    let description : String?
    let points : Int?
    let id : String?
    
    enum CodingKeys: String, CodingKey {        
        case name = "Name"
        case description = "Description"
        case points = "Points"
        case id = "Id"
    }
}
