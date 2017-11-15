//
//  user.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class User {
    var id: Int
    var username: String
    var password: String
    var isLoggedIn: Bool
    
    init(id: Int, isLoggedIn: Bool) {
        self.id = id
        self.isLoggedIn = isLoggedIn
    }
}
