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
    
    func getMockUser(withId Id: String) -> User? {
        users = getMockUsers()
        
        for user in users {
            if user.id == Id{
                return user
            }
        }
        
        return nil
    }
    
    func getMockUsers() -> [User]{
        var users: [User] = []
        
        users.append(User(id: "Bob", firstname: "Groenteboer", lastname: "Bob", role: Role.CLIENT))
        users.append(User(id: "Harry", firstname: "Harry", lastname: "De Man", role: Role.CAREGIVER))
        users.append(User(id: "Sjors", firstname: "Sjors", lastname: "De Groot", role: Role.RELATIVE))
        
        return users
    }
    
    func getUsers() {
        apiClient.send(toRelativePath: "user",
                       withHttpMethod: .get,
                       onSuccessParser: { (data) in
                        print(data)
        }) {
        }
        
        
    }
    
    func getUser(withId Id: String) {
//            apiClient.send(toRelativePath: <#T##String#>, withHttpMethod: <#T##HTTPMethod#>, onSuccessParser: <#T##(Data) -> ()#>, onFailure: <#T##() -> ()#>)
    }
}
