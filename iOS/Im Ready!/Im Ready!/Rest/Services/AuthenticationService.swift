//
//  AuthenticationService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import SwiftyJSON

public class AuthenticationService : Service {
    
    /**
     Makes a http call to /login and parses the incoming data
     
     - parameters:
     - username: The username used to login
     - password: The password used to login
     - onSuccess: Closure to call if the data was retrieved and parsed succesfully
     - onFailure: Closure to call if the data could not be retrieved or parsed
     
     - returns: The URLSessionTask object that makes the request. By capturing this object, a request can be cancelled at any time by calling .cancel()
     */
    public func login(withUsername username: String,
                      andPassword password: String,
                      onSuccess: @escaping () -> (),
                      onFailure: @escaping () -> ()) -> () {
        // setup http parameters
        var params = [String : Any]()
        params["username"] = username
        params["password"] = password
        
        apiClient.send(toRelativePath: "login",
                       withHttpMethod: .post,
                       withParameters: params,
                       onSuccessParser: { (_ data) in
                        do {
                            _ = try JSONDecoder().decode(
                                LoginResult.self, from: data)
                            onSuccess()
                        }catch {
                            onFailure()
                        }
                        
        }, onFailure: onFailure)
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
