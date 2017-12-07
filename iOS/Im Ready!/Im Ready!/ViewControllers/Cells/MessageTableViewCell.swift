//
//  ChatTableViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 04/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class MessageTableViewCell: UITableViewCell {
    @IBOutlet weak var recipientImage: UIImageView!
    @IBOutlet weak var recipientName: UILabel!
    @IBOutlet weak var recentMessage: UILabel!
    @IBOutlet weak var cellView: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
