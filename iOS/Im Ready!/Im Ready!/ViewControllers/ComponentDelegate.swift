//
//  ComponentDelegate.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 18/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import Foundation

protocol ComponentDelegate: class {
    func passAddedComponent(component: Component, index: IndexPath)
}
