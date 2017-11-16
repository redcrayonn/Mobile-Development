//
//  KeyboardSettings.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

class KeyboardSettings{
//    func registerForKeyboardNotifications()
//    {
//        //Adding notifies on keyboard appearing
//        NSNotificationCenter.defaultCenter().addObserver(self, selector: "keyboardWasShown:", name: UIKeyboardWillShowNotification, object: nil)
//        NSNotificationCenter.defaultCenter().addObserver(self, selector: "keyboardWillBeHidden:", name: UIKeyboardWillHideNotification, object: nil)
//    }
//    
//    func deregisterFromKeyboardNotifications()
//    {
//        //Removing notifies on keyboard appearing
//        NSNotificationCenter.defaultCenter().removeObserver(self, name: UIKeyboardWillShowNotification, object: nil)
//        NSNotificationCenter.defaultCenter().removeObserver(self, name: UIKeyboardWillHideNotification, object: nil)
//    }
//    
//    func keyboardWasShown(notification: NSNotification)
//    {
//        //Need to calculate keyboard exact size due to Apple suggestions
//        self.scrollView.scrollEnabled = true
//        var info : NSDictionary = notification.userInfo!
//        var keyboardSize = (info[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.CGRectValue().size
//        var contentInsets : UIEdgeInsets = UIEdgeInsetsMake(0.0, 0.0, keyboardSize!.height, 0.0)
//        
//        self.scrollView.contentInset = contentInsets
//        self.scrollView.scrollIndicatorInsets = contentInsets
//        
//        var aRect : CGRect = self.view.frame
//        aRect.size.height -= keyboardSize!.height
//        if let activeFieldPresent = activeField
//        {
//            if (!CGRectContainsPoint(aRect, activeField!.frame.origin))
//            {
//                self.scrollView.scrollRectToVisible(activeField!.frame, animated: true)
//            }
//        }
//        
//        
//    }
//    
//    
//    func keyboardWillBeHidden(notification: NSNotification)
//    {
//        //Once keyboard disappears, restore original positions
//        var info : NSDictionary = notification.userInfo!
//        var keyboardSize = (info[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.CGRectValue().size
//        var contentInsets : UIEdgeInsets = UIEdgeInsetsMake(0.0, 0.0, -keyboardSize!.height, 0.0)
//        self.scrollView.contentInset = contentInsets
//        self.scrollView.scrollIndicatorInsets = contentInsets
//        self.view.endEditing(true)
//        self.scrollView.scrollEnabled = false
//        
//    }
//    
//    func textFieldDidBeginEditing(textField: UITextField!)
//    {
//        activeField = textField
//    }
//    
//    func textFieldDidEndEditing(textField: UITextField!)
//    {
//        activeField = nil
//    }
}
