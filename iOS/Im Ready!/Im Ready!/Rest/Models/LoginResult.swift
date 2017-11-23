//
//  LoginResult.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
class LoginResult : Decodable {
    let authtoken : String?
    let datetime : Int
    
    enum CodingKeys: String, CodingKey {        
        case authtoken = "authtoken"
        case datetime = "datetime"
    }
}

