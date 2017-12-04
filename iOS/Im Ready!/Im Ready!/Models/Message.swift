//
//  Message.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Message {
    var id: String?
    var senderId: String?
    var receiverId: String?
    var datetime: String?
    var message: String?
    var read: Bool?
    
    init(id: String, senderId: String, receiverId: String, datetime: String, message: String, read: Bool) {
        self.id = id
        self.senderId = senderId
        self.receiverId = receiverId
        self.datetime = datetime
        self.message = message
        self.read = read
    }
}
