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
    @IBOutlet weak var cellBG: UIView!
    @IBOutlet weak var chevron: UIImageView!
    
    var component: ClientComponent?
    var activites: [Activity]?
}
