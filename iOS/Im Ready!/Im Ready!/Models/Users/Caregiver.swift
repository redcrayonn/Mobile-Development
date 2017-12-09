//
//  Caregiver.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Caregiver : EntityModel, User {
    var clients : [Client]?
    var caregiverRole : Role = Role.CAREGIVER
}
