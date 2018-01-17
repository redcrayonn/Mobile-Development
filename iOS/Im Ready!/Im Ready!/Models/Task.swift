//
//  Task.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import Foundation
class Task : Decodable {
    let name : String?
    let deadlineDate : String?
    let description : String?
    let status : Int?
    let feedback : String?
    let id : String?
    
    enum CodingKeys: String, CodingKey {
        
        case name = "Name"
        case deadlineDate = "DeadlineDate"
        case description = "Description"
        case status = "Status"
        case feedback = "Feedback"
        case id = "Id"
    }
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        deadlineDate = try values.decodeIfPresent(String.self, forKey: .deadlineDate)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        status = try values.decodeIfPresent(Int.self, forKey: .status)
        feedback = try values.decodeIfPresent(String.self, forKey: .feedback)
        id = try values.decodeIfPresent(String.self, forKey: .id)
    }
    
}
