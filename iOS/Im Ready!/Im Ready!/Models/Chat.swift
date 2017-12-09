//
//  Chat.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import SwiftKeychainWrapper

class Chat : EntityModel {
    var senderId: String?
    var receiverId: String?
    
    enum CodingKeys: String, CodingKey {
        case senderId = "senderId"
        case receiverId = "receiverId"
    }
}
