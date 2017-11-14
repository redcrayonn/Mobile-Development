//
//  Person.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Person {
    let id: Int
    let firstName: String
    let lastName: String
    let birthDate: NSDate
    let email: String
    let phoneNumber: Int
        
    init(id: Int, firstName: String, lastName: String, birthDate: NSDate,
         email: String, phoneNumber: Int) {
        self.id = id
        self.firstName = firstName
        self.lastName = lastName
        self.birthDate = birthDate
        self.email = email
        self.phoneNumber = phoneNumber
    }
}
