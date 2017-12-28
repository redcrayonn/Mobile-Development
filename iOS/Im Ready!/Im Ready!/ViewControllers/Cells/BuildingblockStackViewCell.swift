//
//  BuildingblockStackViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 28/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class BuildingblockStackViewCell: UICollectionViewCell {
    
    var cellExists:Bool = false
    
    @IBOutlet var openView: UIView!
    
    @IBOutlet var detailView: UIView! {
        didSet {
            detailView?.isHidden = true
            detailView?.alpha = 0
        }
    }
    
    @IBOutlet var title: UIButton!
    @IBOutlet var textView: UITextView!
    @IBOutlet var add: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func animate(duration:Double, onSuccess: @escaping () -> Void) {
        UIView.animateKeyframes(withDuration: duration, delay: 0, options: .calculationModePaced, animations: {
            UIView.addKeyframe(withRelativeStartTime: 0, relativeDuration: duration, animations: {
                
                self.detailView.isHidden = !self.detailView.isHidden
                if self.detailView.alpha == 1 {
                    self.detailView.alpha = 0.5
                } else {
                    self.detailView.alpha = 1
                }
                
            })
        }, completion: {  (finished: Bool) in
            print("animation complete")
            onSuccess()
        })
    }
}
