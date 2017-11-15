//
//  user.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

// Singleton User
class User {
    let id: String? = nil
    let username: String? = nil
    let password: String? = nil
    let authToken: String? = nil
    let datetime: String? = nil
    
    var isLoggedIn: Bool {
        get {
            return authToken != nil
        }
    }
    
    init() {}
    
    private static var instance: User?
    
    static func current() -> User {
        if instance == nil {
            instance = User()
        }
        return instance!
    }
    
//    enum CodingKeys: String, CodingKey {
//        case authtoken = "authtoken"
//        case datetime = "datetime"
//    }
}
