//
//  AuthenticationService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

public class AuthenticationService : Service {
    public func login(withUsername username: String,
                      andPassword password: String,
                      onSuccess: @escaping () -> (),
                      onFailure: @escaping () -> ()) -> () {
        // setup http parameters
        var params = [String : Any]()
        params["username"] = username
        params["password"] = password
        params["grant_type"] = "password"
        
        var headers = [String: String]()
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        headers["Accept"] = "application/json"
        
        apiClient.send(toRelativePath: "login",
                       withHttpMethod: .post,
                       withParameters: params,
                       withHeaders: headers,
                       withEncoding: URLEncoding.httpBody,
                       onSuccessParser: { (_ data) in
                        do {
                            // Make a dict of the JSON to check for errors
                            let dictionary: NSDictionary = try JSONSerialization.jsonObject(
                                with: data, options: []) as? [String:AnyObject] as! NSDictionary
                            if let error = dictionary["error"]{
                                print(error)
                                onFailure()
                            } else {
                                let results = try JSONDecoder().decode(
                                    LoginResult.self, from: data)
                                
                                self.setCurrentUser(results: results)
                                
                                onSuccess()
                            }
                        }catch {
                            onFailure()
                        }
                        
        }, onFailure: onFailure)
    }
    
    func setCurrentUser(results: LoginResult) {
        CurrentUser.instance.access_token = results.access_token
        CurrentUser.instance.id = results.user_id
        CurrentUser.instance.username = results.firstname
        CurrentUser.instance.user_type = Role(rawValue: results.user_type!)
    }
    
    
    public func logOut() -> Bool {
        let preferences = UserDefaults.standard
        preferences.set(nil, forKey: "session")
        return true
    }
    
    public func isLoggedIn() -> Bool {
        let preferences = UserDefaults.standard
        let authtoken = preferences.string(forKey: "session")
        return authtoken != nil
    }
}
