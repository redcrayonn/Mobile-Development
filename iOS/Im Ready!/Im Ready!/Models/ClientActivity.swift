//
//  ClientActivities.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class ClientActivity : Decodable {
    let name : String?
    let description : String?
    let points : Int?
    var status: Int?
    var answer: String?
    let deadline: String?
    let feedback: [Feedback]?
    let id : String?
    
    enum CodingKeys: String, CodingKey {        
        case name = "Name"
        case description = "Description"
        case points = "Points"
        case id = "Id"
        case answer = "Content"
        case feedback = "Feedback"
        case status = "Status"
        case deadline = "Deadline"
    }
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        name = try values.decodeIfPresent(String.self, forKey: .name)
        description = try values.decodeIfPresent(String.self, forKey: .description)
        points = try values.decodeIfPresent(Int.self, forKey: .points)
        id = try values.decodeIfPresent(String.self, forKey: .id)
        answer = try values.decodeIfPresent(String.self, forKey: .answer)
        feedback = try values.decodeIfPresent([Feedback].self, forKey: .feedback)
//        feedback = try [Feedback](from: decoder)
        status = try values.decodeIfPresent(Int.self, forKey: .status)
        deadline = try values.decodeIfPresent(String.self, forKey: .deadline)
    }

}
