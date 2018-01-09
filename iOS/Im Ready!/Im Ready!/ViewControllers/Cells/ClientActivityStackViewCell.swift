//
//  ActivityTableViewCell.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 30/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ClientActivityStackViewCell: UITableViewCell {
    @IBOutlet weak var openView: UIView!
    @IBOutlet weak var detailView: UIView! {
        didSet {
            detailView.isHidden = true
            detailView.alpha = 0
        }
    }
    @IBOutlet weak var openDetailViewBtn: UIButton!
    @IBOutlet weak var activityDescription: UILabel!
    @IBOutlet weak var answerTextView: UITextView!
    @IBOutlet weak var sendAnswerBtn: UIButton!
    
    var cellExists: Bool = false
    var activity: ClientActivity?
    var view: ClientActivityViewController!
//    var content: String?
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state
    }
    
    @IBAction func sendAnswer(_ sender: Any) {
        let alert = UIAlertController(title: "Antwoord versturen?", message: "Weet je zeker dat je het in wilt leveren?", preferredStyle: UIAlertControllerStyle.alert)
        alert.addAction(UIAlertAction(title: "Ja", style: UIAlertActionStyle.default, handler: { (action) in
            
            let parameters: [String: Any] = ["Status": 1,
                                             "Content": self.answerTextView.text]
            
            clientService.sendAnswer(clientId: CurrentUser.instance.id!,
                                     activityId: (self.activity?.id)!,
                                     parameters: parameters,
                                     onSuccess: {
                                        simpleAlert(atVC: self.view,
                                                    withTitle: "Gelukt!",
                                                    andMessage: "Je antwoord is opgestuurd!")
                                        
                                        self.sendAnswerBtn.isHidden = true
                                        self.answerTextView.isEditable = false
            }, onFailure: {
                print("something went wrong putting the answer")
                simpleAlert(atVC: self.view, withTitle: "Er is iets fout gegaan", andMessage: "Er is iets fout gegaan met het opsturen van je antwoord, probeer het later nog eens.")
            })
        }))
        
        alert.addAction(UIAlertAction(title: "Nee", style: UIAlertActionStyle.cancel, handler: nil))
        
        view.present(alert, animated: true, completion: nil)
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
