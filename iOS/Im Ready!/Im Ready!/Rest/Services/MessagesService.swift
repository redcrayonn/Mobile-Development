//
//  MessagesService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 04/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class MessagesService : Service {
    func getMockMessages() -> [Message] {
        var messages: [Message] = []
        
        messages.append(Message(id: "1", senderId: "1", receiverId: "2", datetime: "04-12-2017", message: "Hoi Klaas", read: false))
        messages.append(Message(id: "2", senderId: "2", receiverId: "1", datetime: "04-12-2017", message: "Hoi Fred", read: true))
        
        return messages
    }
    
    func getMessages() {
        let clientId = "5a16a1710f39e85531f0494f"
        apiClient.send(toRelativePath: "/clients/\(clientId)/messages", withHttpMethod: .get, onSuccessParser: { (_ data: Data) in
            print("success")
            print(data)
        }) {
            print("failure")
        }
    }
}
