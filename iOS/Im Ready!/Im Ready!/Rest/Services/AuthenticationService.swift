//
//  AuthenticationService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class AuthenticationService : Service {
        
    /**
     Makes a http call to /api/users/login and parses the incoming data
     
     - parameters:
     - username: The username used to login
     - password: The password used to login
     - onSucces: Closure to call if the data was retrieved and parsed succesfully
     - onFailure: Closure to call if the data could not be retrieved or parsed
     
     - returns: The URLSessionTask object that makes the request. By capturing this object, a request can be cancelled at any time by calling .cancel()
     */
    public func login(withUsername username: String,
                      password: String,
                      onSuccess: @escaping () -> (),
                      onFailure: @escaping () -> ()) -> () {
        // setup http params        
        var params = [String : Any]()
        params["username"] = username
        params["password"] = password
        
        onSuccess()
        // call to network layer
//        return apiClient.send(toRelativePath: "/api/users/login/",
//                              withHttpMethod: HttpMethod.POST,
//                              withParameters: params,
//                              onSuccesParser: { (_ data : Data) in
//
//                                // business logic after succesfull network layer request
//                                // Parses the data gotten from the login call
//                                do {
//                                    let result = try JSONDecoder().decode(LoginResult.self, from: data)
//
//                                    // get the user defaults database and save the auth token to it
//                                    let preferences = UserDefaults.standard
//                                    preferences.set(result.authtoken, forKey: "session")
//
//                                    // call caller succes closure
//                                    onSucces()
//                                } catch {
//                                    // soemthing went wrong, call caller failure closure
//                                    onFailure()
//                                }
//
//        }, onFailure: onFailure)
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
