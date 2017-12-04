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
    
    var cellExists: Bool = false
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
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
