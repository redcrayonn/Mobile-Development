//
//  ComponentCollectionViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 07/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ClientComponentCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var buildingblockImageView: UIImageView!
    @IBOutlet weak var activitiesLbl: UILabel!    
    
    var component: ClientComponent?
    var activites: [Activity]?
    
    func styleCell() {
        self.layer.cornerRadius = 6
        self.layer.masksToBounds = false
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.6
        self.layer.shadowOffset = CGSize(width: 1, height: 1)
        self.layer.shadowRadius = 4        
    }
}
