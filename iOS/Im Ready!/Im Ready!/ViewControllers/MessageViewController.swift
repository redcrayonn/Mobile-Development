//
//  MessageViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 11/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class MessageViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var tableView: UITableView!
    var recipient: User!
    
    var messages: [Message] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = recipient?.firstname
        messages = messageService.getMockMessages()
        
        self.tableView.dataSource = self
        self.tableView.delegate = self
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return messages.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        var cell: UITableViewCell?
        
        for message in messages {
            if message.senderId != recipient.id {
                cell = tableView.dequeueReusableCell(withIdentifier: "SendingMessageCell", for: indexPath)
                    as! SendingMessageTableViewCell
            } else {
                cell = tableView.dequeueReusableCell(withIdentifier: "IncomingMessageCell", for: indexPath)
                    as! IncomingMessageTableViewCell
            }
        }
        
        return cell!
    }
    
}
