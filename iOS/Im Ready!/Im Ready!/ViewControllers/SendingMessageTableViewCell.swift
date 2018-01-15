//
//  MessageCollectionViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 11/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class SendingMessageTableViewCell: UITableViewCell {
    @IBOutlet var name: UILabel?
    @IBOutlet var message: UILabel?
    @IBOutlet var datetime: UILabel?
    
    var chat: Chat?
    var receiver: User?
}
