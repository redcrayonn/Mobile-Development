//
//  MessageService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 12/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class MessageService : Service {
    
    func getMockMessages() -> [Message] {
        var messages: [Message] = []
        
        messages.append(Message(id: "1", chatId: "1", content: "Hoi Bob!", sentDate: Date(), read: true, userId: "1"))
        messages.append(Message(id: "2", chatId: "1", content: "Hoi Wouter!", sentDate: Date(), read: false, userId: "1"))
        messages.append(Message(id: "3", chatId: "1", content: "Alles goed?", sentDate: Date(), read: false, userId: "1"))
        
        return messages
    }
}
