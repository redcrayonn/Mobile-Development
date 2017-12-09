//
//  Message.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Message : EntityModel {
    var chatId : String?
    var content : String?
    var sentDate : Date?
    var read : Bool?
    var userId : String?
    
    enum CodingKeys: String, CodingKey {
        case chatId = "chatId"
        case content = "content"
        case sentDate = "sentDate"
        case read = "read"
        case userId = "userId"
    }
}
