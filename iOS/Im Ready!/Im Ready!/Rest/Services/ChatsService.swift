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
        chats.append(Chat(id: "test", senderId: "Wouter", receiverId: "Harry"))
        
        return chats
    }
    
    func getChats(forUser id: String) {
        apiClient.send(toRelativePath: "/clients/\(id)/messages", withHttpMethod: .get, onSuccessParser: { (_ data: Data) in
            print("success")
            print(data)
        }) {
            print("failure")
        }
    }
}
