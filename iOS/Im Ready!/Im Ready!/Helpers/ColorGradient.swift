//
//  ColorGradient.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import Foundation
import UIKit

public let hexColorGradient: [String] = ["40185E", "8E327E", "B00F5A", "CC003F", "DD1730", "F16122"]

/// Set the color of the object based on the index
public func setColor(forIndex index: IndexPath) -> String {
    var usedColors: [String] = []
    if index.row < hexColorGradient.count {
        usedColors.append(hexColorGradient[index.row])
        
        return hexColorGradient[index.row]
    } else {
        let x = index.row % hexColorGradient.count
        let i = hexColorGradient.count - 1

        return hexColorGradient[i - x]
    }
}
