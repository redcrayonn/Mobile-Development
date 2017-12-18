//
//  checkInternetConnection.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import Foundation
import SystemConfiguration

public let internetConnection: InternetConnection = InternetConnection()

public class InternetConnection {
    
    func isInternetAvailable() -> Bool
    {
        var zeroAddress = sockaddr_in()
        zeroAddress.sin_len = UInt8(MemoryLayout.size(ofValue: zeroAddress))
        zeroAddress.sin_family = sa_family_t(AF_INET)
        
        let defaultRouteReachability = withUnsafePointer(to: &zeroAddress) {
            $0.withMemoryRebound(to: sockaddr.self, capacity: 1) {zeroSockAddress in
                SCNetworkReachabilityCreateWithAddress(nil, zeroSockAddress)
            }
        }
        
        var flags = SCNetworkReachabilityFlags()
        if !SCNetworkReachabilityGetFlags(defaultRouteReachability!, &flags) {
            return false
        }
        let isReachable = flags.contains(.reachable)
        let needsConnection = flags.contains(.connectionRequired)
        
        return (isReachable && !needsConnection)
    }
    
    func noInternetConnectionError(atVC: UIViewController,
                                   tryAgain: @escaping () -> ()) -> () {
        let noConnectionAlert = UIAlertController(title: "No internet connection",
                                                  message: "",
                                                  preferredStyle: UIAlertControllerStyle.alert)
        noConnectionAlert.addAction(UIAlertAction(title: "Try again", style: .default,
                                                  handler: { (action: UIAlertAction!) in
                                                    tryAgain()
        }))
        
        atVC.present(noConnectionAlert, animated: true, completion: nil)
    }
    
    // Check internetconnection
    func ifConnectedToInternet(atVC: UIViewController,
                               whenConnected: @escaping () -> (),
                               retryConnection: @escaping () -> ()) {
        if internetConnection.isInternetAvailable() {
            whenConnected()
        } else {
            internetConnection.noInternetConnectionError(atVC: atVC, tryAgain: {
                whenConnected()
            })
        }
    }
}
