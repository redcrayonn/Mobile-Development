//
//  Family.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Family : Person {
    let familyMemberId: Int
    
    init(id: Int, firstName: String, lastName: String, birthDate: NSDate, email: String, phoneNumber: Int, familyMemberId: Int) {
        self.familyMemberId = familyMemberId
        super.init(id: id, firstName: firstName, lastName: lastName, birthDate: birthDate, email: email, phoneNumber: phoneNumber)
    }
    
}
