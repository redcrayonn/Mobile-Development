//
//  Chat.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Chat {
    var id: Int
    var senderId: Person
    var receiverId: Person
    
    init(id: Int, senderId: Person, receiverId: Person) {
        self.id = id
        self.senderId = senderId
        self.receiverId = receiverId
    }
}
