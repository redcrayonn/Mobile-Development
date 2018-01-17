//
//  LoadingSpinner.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 19/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import UIKit

// A public activityIndicator that can be called from everywhere
public let activityIndicator : UIActivityIndicatorView = UIActivityIndicatorView()

/// Function to start an ActivityIndicator with optional backgroundView
/// User interactions will be ignored
public func startActivityIndicator(atVC viewController: UIViewController,
                                   withView view: UIView,
                                   andIndicatorBGView indicatorBG: UIView?) {
    if indicatorBG != nil {
        indicatorBG?.isHidden = false
    }
    activityIndicator.center = viewController.view.center
    activityIndicator.hidesWhenStopped = true
    activityIndicator.activityIndicatorViewStyle = UIActivityIndicatorViewStyle.whiteLarge
    view.addSubview(activityIndicator)
    activityIndicator.startAnimating()
    UIApplication.shared.beginIgnoringInteractionEvents()
}

/// Stop the activityIndicator and hide the optional background again
/// User interactions allowed again
public func stopActivityIndicator(withIndicatorBGView indicatorBG: UIView?) {
    if indicatorBG != nil {
        indicatorBG?.isHidden = true
    }
    activityIndicator.stopAnimating()
    UIApplication.shared.endIgnoringInteractionEvents()
}
