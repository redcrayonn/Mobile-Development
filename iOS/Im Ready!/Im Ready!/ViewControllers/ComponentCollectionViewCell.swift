//
//  ActivityTableViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 04/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit

class ComponentCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var componentName: UILabel!
    @IBOutlet weak var componentAddBtn: UIButton!
    
    func styleCell() {
        self.layer.cornerRadius = 6.0
        self.layer.masksToBounds = false
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.6
        self.layer.shadowOffset = CGSize(width: 5, height: 3)
        self.layer.shadowRadius = 3
    }
}
