//
//  Relative.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Relative : EntityModel, User {
    var clientId : String?
    var relativeRole : Role = Role.RELATIVE
}
