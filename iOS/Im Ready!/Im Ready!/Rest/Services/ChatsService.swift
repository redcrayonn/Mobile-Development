//
//  ChatsService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 04/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class ChatsService : Service {
    func getMockChats() -> [Chat] {
        var chats: [Chat] = []
        
        chats.append(Chat(id: "test", senderId: "Wouter", receiverId: "Bob"))
        
        return chats
    }
    
    func getChats() {
        let clientId = "5a16a1710f39e85531f0494f"
        apiClient.send(toRelativePath: "/clients/\(clientId)/messages", withHttpMethod: .get, onSuccessParser: { (_ data: Data) in
            print("success")
            print(data)
        }) {
            print("failure")
        }
    }
}
