//
//  Extensions.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
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

extension String {    
    /// Percent escapes values to be added to a URL query as specified in RFC 3986
    ///
    /// This percent-escapes all characters besides the alphanumeric character set and "-", ".", "_", and "~".
    ///
    /// http://www.ietf.org/rfc/rfc3986.txt
    ///
    /// - returns: Returns percent-escaped string.
    
    func addingPercentEncodingForURLQueryValue() -> String? {
        let generalDelimitersToEncode = ":#[]@" // does not include "?" or "/" due to RFC 3986 - Section 3.4
        let subDelimitersToEncode = "!$&'()*+,;="
        
        var allowed = CharacterSet.urlQueryAllowed
        allowed.remove(charactersIn: generalDelimitersToEncode + subDelimitersToEncode)
        
        return addingPercentEncoding(withAllowedCharacters: allowed)
    }
}

extension Dictionary {
    /// Build string representation of HTTP parameter dictionary of keys and objects
    ///
    /// This percent escapes in compliance with RFC 3986
    ///
    /// http://www.ietf.org/rfc/rfc3986.txt
    ///
    /// - returns: String representation in the form of key1=value1&key2=value2 where the keys and values are percent escaped
    
    func stringFromHttpParameters() -> String {
        let parameterArray = map { key, value -> String in
            let percentEscapedKey = (key as! String).addingPercentEncodingForURLQueryValue()!
            var percentEscapedValue: String
            if let val = (value as? String) {
                percentEscapedValue = val.addingPercentEncodingForURLQueryValue()!
            } else {
                // in case the type could not be casted (Swift primitive types can not be cast to/from each other) -> create a new string containing the contents
                percentEscapedValue = String(describing: value).addingPercentEncodingForURLQueryValue()!
            }
            return "\(percentEscapedKey)=\(percentEscapedValue)"
        }
        
        return parameterArray.joined(separator: "&")
    }
}

//Extension for UIImageView to facilitate a border radius
extension UIImageView
{
    func roundCornersForAspectFit(radius: CGFloat)
    {
        if let image = self.image {
            
            //calculate drawingRect
            let boundsScale = self.bounds.size.width / self.bounds.size.height
            let imageScale = image.size.width / image.size.height
            
            var drawingRect: CGRect = self.bounds
            
            if boundsScale > imageScale {
                drawingRect.size.width =  drawingRect.size.height * imageScale
                drawingRect.origin.x = (self.bounds.size.width - drawingRect.size.width) / 2
            } else {
                drawingRect.size.height = drawingRect.size.width / imageScale
                drawingRect.origin.y = (self.bounds.size.height - drawingRect.size.height) / 2
            }
            let path = UIBezierPath(roundedRect: drawingRect, cornerRadius: radius)
            let mask = CAShapeLayer()
            mask.path = path.cgPath
            self.layer.mask = mask
        }
    }
}

