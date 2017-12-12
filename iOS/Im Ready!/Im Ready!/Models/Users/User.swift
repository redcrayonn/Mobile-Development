//
//  user.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class User : EntityModel {
    var firstname : String?
    var lastname : String?
    var role : Role?
    
    init(id: String, firstname: String, lastname: String, role: Role){
        super.init()
        self.id = id
        self.firstname = firstname
        self.lastname = lastname
        self.role = role
    }
    
}
