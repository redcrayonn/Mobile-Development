//
//  ComponentCollectionViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 07/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ComponentCollectionViewCell: UICollectionViewCell {
    @IBOutlet weak var name: UILabel!
    var component: Component?
    var activites: [Activity]?
}
