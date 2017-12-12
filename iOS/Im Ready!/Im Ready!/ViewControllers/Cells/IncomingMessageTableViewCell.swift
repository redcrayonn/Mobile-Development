//
//  IncomingMessageTableViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 12/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class IncomingMessageTableViewCell: UITableViewCell {
    @IBOutlet var name: UILabel?
    @IBOutlet var message: UILabel?
    @IBOutlet var datetime: UILabel?
    
    var chat: Chat?
    var receiver: User?

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
