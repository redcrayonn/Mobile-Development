//
//  HomescreenCollectionViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class BuildingblockCell : UICollectionViewCell {
    @IBOutlet weak var buildingblockImage: UIImageView!
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var backgroundImage: UIImageView!
    
    var buildingblock: Buildingblock?
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    // Somehow add borderradius to the backgroundimage here
    override init(frame: CGRect) {
        super.init(frame: frame)        
//        self.backgroundImage.layer.cornerRadius = 2.0
//        self.backgroundImage.layer.borderWidth = 1.0
//        self.backgroundImage.layer.borderColor = UIColor.clear.cgColor
//        self.backgroundImage.layer.masksToBounds = true
//
//        self.layer.shadowColor = UIColor.lightGray.cgColor
//        self.layer.shadowOffset = CGSize(width: 0, height: 2.0)
//        self.layer.shadowRadius = 2.0
//        self.layer.shadowOpacity = 1.0
//        self.layer.masksToBounds = false
//        self.layer.shadowPath = UIBezierPath(roundedRect: self.bounds, cornerRadius: self.contentView.layer.cornerRadius).cgPath

//        self.backgroundImage!.layer.shadowPath = UIBezierPath(roundedRect: outerView.bounds, cornerRadius: 10).cgPath

//        self.backgroundImage!.layer.masksToBounds = true

//        self.backgroundImage!.layer.cornerRadius = CGRectGetWidth(
//            (self.backgroundImage!.frame))/4.0
    }
}
