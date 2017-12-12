//
//  Client.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class Client : User {
    var points : Int?
    var caregiverId : String?
    var buildingblocks : [Buildingblock]?
    var relatives : [Relative]?    
    var clientRole : Role?
}
