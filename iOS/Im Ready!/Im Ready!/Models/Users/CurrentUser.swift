//
//  CurrentUser.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class CurrentUser {
    static let instance = CurrentUser()
    
    var id : String?
    var access_token: String?
    var username: String?
    var user_type : Role?
    var isLoggedIn: Bool {
        get{
            return access_token != nil
        }
    }
}
