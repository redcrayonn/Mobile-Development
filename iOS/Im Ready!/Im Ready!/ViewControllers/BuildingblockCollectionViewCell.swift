//
//  BuildingblockStackViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 28/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class BuildingblockCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var buildingblockImage: UIImageView!
    @IBOutlet weak var buildingblockName: UILabel!
    @IBOutlet weak var componentsLbl: UILabel!
    
    var components: [Component] = []
    
    func styleCell() {
        self.layer.cornerRadius = 6.0
        self.layer.masksToBounds = false
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.6
        self.layer.shadowOffset = CGSize(width: -1, height: 1)
        self.layer.shadowRadius = 3
    }
}
