//
//  Alerts.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 28/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import UIKit

public func simpleAlert(atVC viewController: UIViewController,
                        withTitle title: String,
                        andMessage message: String) {
    let alertController = UIAlertController(title: title,
                                            message: message,
                                            preferredStyle: UIAlertControllerStyle.alert)
    alertController.addAction(UIAlertAction(title: "Ok", style: UIAlertActionStyle.default,handler: nil))
    
    viewController.present(alertController, animated: true, completion: nil)

}
