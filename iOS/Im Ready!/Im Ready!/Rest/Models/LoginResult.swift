//
//  LoginResult.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
class LoginResult : Decodable {    
    let access_token : String!
    let token_type : String!
    let expires_in : Double!
    let user_type : String!
    let user_id : String!
    let firstname : String!
    let issued : String!
    let expires : String!
    
    enum CodingKeys: String, CodingKey {        
        case access_token
        case token_type
        case expires_in
        case user_type
        case user_id
        case firstname
        case issued = ".issued"
        case expires = ".expires"
    }
}

