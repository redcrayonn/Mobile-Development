//
//  ChatViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 04/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import SwiftKeychainWrapper

class MessagesViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var tableView: UITableView!
    
    var messages: [Message] = []
    let cellSpacingHeight: CGFloat = 5

    override func viewDidLoad() {
        super.viewDidLoad()
        
//        messagesService.getMessages()
        messages = messagesService.getMockMessages()

        tableView.delegate = self
        tableView.dataSource = self

        // Do any additional setup after loading the view.
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return messages.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MessageCell", for: indexPath) as! MessageTableViewCell
        
        cell.recentMessage.text = messages[indexPath.row].message
        cell.cellView.layer.cornerRadius = 6
        cell.cellView.layer.borderColor = UIColor.black.cgColor
        cell.cellView.layer.borderWidth = 1.0
        cell.cellView.layer.shadowColor = UIColor.black.cgColor;
        cell.cellView.layer.shadowRadius = 5.0;
        cell.cellView.layer.shadowOffset = CGSize(width: 10, height: 10)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    
    // Set the spacing between sections
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return cellSpacingHeight
    }
}
