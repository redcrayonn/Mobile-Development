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
    @IBOutlet weak var chevronImageView: UIImageView!
    @IBOutlet weak var activitiesLbl: UILabel!
    
    var component: ClientComponent?
    var activites: [Activity]?
}
