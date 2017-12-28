//
//  UserService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 12/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class UserService : Service {
    var users: [User] = []
    
    func getUsers() {
        apiClient.send(toRelativePath: "user",
                       withHttpMethod: .get,
                       onSuccessParser: { (data) in
                        print(data)
        }) {
            print("something failed at getUsers()")
        }
        
        
    }
    
    func getUser(withId Id: String) {
        apiClient.send(toRelativePath: "user\(Id)",
            withHttpMethod: .get,
            onSuccessParser: { (data) in
                print(data)
        }) {
            print("Failed to retrieve user")
        }
    }
}
