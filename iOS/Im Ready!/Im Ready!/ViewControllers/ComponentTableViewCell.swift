 //
//  ComponentTableViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 25/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ComponentTableViewCell: UITableViewCell {
    @IBOutlet weak var componentPoints: UILabel!
    @IBOutlet weak var componentName: UILabel!
    @IBOutlet weak var cellView: UIView!
    
    var component: Component?

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    }
    
    func fill(withComponent component: Component) {
        self.component = component
        
        componentName.text = component.name
        componentPoints.text = getPoints()        
    }
    
    func getPoints() -> String {
        
        return "15"
    }
}
