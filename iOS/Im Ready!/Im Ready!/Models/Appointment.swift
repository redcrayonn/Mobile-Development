//
//  Appointment.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import Foundation

class Appointment : Decodable {
    let title : String?
    let startDate : String?
    let endDate : String?
    let location : String?
    let remark : String?
    let id : String?
    
    enum CodingKeys: String, CodingKey {
        case title = "Title"
        case startDate = "StartDate"
        case endDate = "EndDate"
        case location = "Location"
        case remark = "Remark"
        case id = "Id"
    }
    
    required init(from decoder: Decoder) throws {
        let values = try decoder.container(keyedBy: CodingKeys.self)
        title = try values.decodeIfPresent(String.self, forKey: .title)
        startDate = try values.decodeIfPresent(String.self, forKey: .startDate)
        endDate = try values.decodeIfPresent(String.self, forKey: .endDate)
        location = try values.decodeIfPresent(String.self, forKey: .location)
        remark = try values.decodeIfPresent(String.self, forKey: .remark)
        id = try values.decodeIfPresent(String.self, forKey: .id)
    }
}

