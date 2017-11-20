//
//  HomescreenCollectionViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class BuildingBlockCell: UICollectionViewCell {
    @IBOutlet weak var buildingBlockImage: UIImageView!
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var backgroundImage: UIImageView!
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    // Somehow add borderradius to the backgroundimage here
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundImage.layoutIfNeeded()
        self.backgroundImage!.layer.cornerRadius = self.frame.width / 4.0
        self.backgroundImage!.layer.masksToBounds = true

//        self.backgroundImage!.layer.cornerRadius = CGRectGetWidth(
//            (self.backgroundImage!.frame))/4.0
    }
}
