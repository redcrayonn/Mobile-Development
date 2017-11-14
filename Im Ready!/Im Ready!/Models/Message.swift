//
//  Message.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Message {
    var id: Int
    var dateTime: Date
//    var time
    var message: String
    
    init(id: Int, dateTime: Date, message: String) {
        self.id = id
        self.dateTime = dateTime
        self.message = message
    }

}
