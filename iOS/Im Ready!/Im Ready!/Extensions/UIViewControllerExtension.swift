//
//  UIViewControllerExtension.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 20/11/2017.
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
    
    func goToTabBarView(inStoryboard storyboard: String, withIdentifier identifier: String) {
        let storyBoard : UIStoryboard = UIStoryboard(name: storyboard, bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(
            withIdentifier: identifier) as! UITabBarController
        self.present(nextViewController, animated:true, completion:nil)
    }
    
}
