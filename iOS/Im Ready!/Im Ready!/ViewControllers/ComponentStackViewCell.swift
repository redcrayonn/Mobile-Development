//
//  ComponentStackViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 28/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ComponentStackViewCell: UITableViewCell {
    @IBOutlet weak var openView: UIView!
    @IBOutlet weak var detailView: UIView! {
        didSet {
            detailView.isHidden = true
            detailView.alpha = 0
        }
    }
    @IBOutlet weak var buildingblockImageView: UIImageView!
    @IBOutlet weak var openDetailViewBtn: UIButton!
    @IBOutlet weak var activityName: UILabel!
    @IBOutlet weak var activityPoints: UILabel!
    
    var cellExists: Bool = false
    var componentActivities: [Activity]?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // do things here
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
    // Fill the activity with sensible data
    func fill(withActivities activities: [Activity]) {
        self.componentActivities = activities
        for activity in componentActivities! {
            self.activityName.text = activity.name
        }
        
    }
    
    // Animation for the dropdown of activities
    func animate(duration:Double, whenDone: @escaping () -> ()) {
        UIView.animateKeyframes(withDuration: duration, delay: 0, options: .calculationModePaced, animations: {
            UIView.addKeyframe(withRelativeStartTime: 0, relativeDuration: duration, animations: {
                self.detailView.isHidden = !self.detailView.isHidden
                if self.detailView.alpha == 1 {
                    self.detailView.alpha = 0.5
                } else {
                    self.detailView.alpha = 1
                }
            })
        }, completion: { (finished: Bool) in
            whenDone()
        })
    }
}
