//
//  Chat.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Chat {
    let id: String
    let sender: Person
    let receiver: Person
    
    init(id: String, sender: Person, receiver: Person) {
        self.id = id
        self.sender = sender
        self.receiver = receiver
    }
}
