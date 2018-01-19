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
    @IBOutlet weak var activitiesLbl: UILabel!
        
    var buildingblock: ClientBuildingblock?
}
