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
}
