//
//  CalendarTableViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 14/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit

class CalendarTableViewCell: UITableViewCell {
    @IBOutlet weak var titleLbl: UILabel!
    @IBOutlet weak var timeFrameLbl: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
