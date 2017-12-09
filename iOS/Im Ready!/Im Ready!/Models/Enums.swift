//
//  Enums.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 09/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

enum Blocktype {
    case LIVING
    case INSURANCE
    case MONEY
    case HEALTH
    case SOCIAL
    case WORK
    case EDUCATION
    case FAMILY
    case RIGHTS_AND_OBLIGATIONS
    case TREATMENT_PLAN
}

enum Role {
    case CLIENT
    case CAREGIVER
    case ADMIN
    case RELATIVE
}

enum Status {
    case ONGOING
    case PENDING
    case DONE
}

enum TriggerLevel {
    case NONE
    case LOW
    case MEDIUM
    case HIGH
    case VERYHIGH
}
