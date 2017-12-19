//
//  Components.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientComponent : Decodable {
    let name : String?
    let description : String?
    let status : Int?
    let activities : [ClientActivities]?
    let deadline : String?
    let component : Component?
    let id : String?
    
    enum CodingKeys: String, CodingKey {
        case name = "Name"
        case description = "Description"
        case status = "Status"
        case activities = "Activities"
        case deadline = "Deadline"
        case component = "Component"
        case id = "Id"
    }

}
