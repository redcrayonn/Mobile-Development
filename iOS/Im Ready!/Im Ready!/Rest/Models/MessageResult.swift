//
//  MessageResult.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 04/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class MessageResult {
    var id: String?
    var senderId: String?
    var receiverId: String?
    var datetime: String?
    var message: String?
    var read: Bool?
    
    enum CodingKeys: String, CodingKey {
        case id = "id"
        case senderId = "senderId"
        case receiverId = "receiverId"
        case datetime = "datetime"
        case read = "read"
    }
}
