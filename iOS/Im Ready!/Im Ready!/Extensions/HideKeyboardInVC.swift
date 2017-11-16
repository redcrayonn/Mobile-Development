//
//  HideKeyboardInVC.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import UIKit
import MobileCoreServices

// Hide keyboard when you click outside the keyboard
extension UIViewController {
    func hideKeyboardWhenTappedAround() {
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(UIViewController.dismissKeyboard))
        tap.cancelsTouchesInView = false
        view.addGestureRecognizer(tap)
    }
    
    @objc func dismissKeyboard() {
        view.endEditing(true)
    }
}
