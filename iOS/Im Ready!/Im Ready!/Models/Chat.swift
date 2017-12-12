//
//  Chat.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Chat : EntityModel {
    var senderId: String?
    var receiverId: String?
    
    enum CodingKeys: String, CodingKey {
        case senderId = "senderId"
        case receiverId = "receiverId"
    }
    
    init(id: String, senderId: String, receiverId: String) {
        super.init()
        self.id = id
        self.senderId = senderId
        self.receiverId = receiverId
    }
}
