//
//  Message.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Message {
    let id: String
    let chat: Chat
    let dateTime: Date
//    var time
    let message: String
    
    init(id: String, dateTime: Date, message: String, chat: Chat) {
        self.id = id
        self.dateTime = dateTime
        self.message = message
        self.chat = chat
    }
}
