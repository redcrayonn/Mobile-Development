//
//  ChooseNewComponentDetailViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit

protocol ComponentDelegate: class {
    func passAddedComponent(component: Component, index: IndexPath)
}

class ChooseNewComponentDetailViewController: UIViewController {
    
    @IBOutlet weak var descriptionTextView: UITextView!
    @IBOutlet weak var activitiesTextView: UITextView!
    @IBOutlet weak var pointsLbl: UILabel!
    @IBOutlet weak var addBtn: UIButton!
    @IBOutlet weak var activityBG: UIView!
    
    var component: Component?
    var componentIndex: IndexPath?
    weak var delegate: ComponentDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = component!.name
        descriptionTextView.text = component?.description
        pointsLbl.text = countTotalPoints(component: component!)
        
        addBtn.layer.cornerRadius = 2.0
        addBtn.layer.masksToBounds = false
        addBtn.layer.shadowColor = UIColor.black.cgColor
        addBtn.layer.shadowOpacity = 0.6
        addBtn.layer.shadowOffset = CGSize(width: 1, height: 1)
        addBtn.layer.shadowRadius = 1

        makeBulletListOfActivities(component: component!)
    }
    
    func makeBulletListOfActivities(component: Component) {
        var activities: [String] = []
        
        guard component.activities != nil else {
            activitiesTextView.text = "Nog geen activiteiten"
            return
        }
        for a in component.activities! {
            activities.append(a.name!)
        }
        
        let attributesDictionary = [NSAttributedStringKey.font : activitiesTextView.font]
        let fullAttributedString = NSMutableAttributedString(string: "", attributes: attributesDictionary)
        
        for string: String in activities
        {
            let bulletPoint: String = "\u{2022}"
            let formattedString: String = "\(bulletPoint) \(string)\n"
            let attributedString: NSMutableAttributedString = NSMutableAttributedString(string: formattedString)
            
            let paragraphStyle = createParagraphAttribute()
            attributedString.addAttributes([NSAttributedStringKey.paragraphStyle: paragraphStyle], range: NSMakeRange(0, attributedString.length))
            
            fullAttributedString.append(attributedString)
        }
        
        activitiesTextView.attributedText = fullAttributedString
    }
    
    func createParagraphAttribute() ->NSParagraphStyle
    {
        var paragraphStyle: NSMutableParagraphStyle
        paragraphStyle = NSParagraphStyle.default.mutableCopy() as! NSMutableParagraphStyle
        paragraphStyle.tabStops = [NSTextTab(textAlignment: .left, location: 15, options: NSDictionary() as! [NSTextTab.OptionKey : Any])]
        paragraphStyle.defaultTabInterval = 15
        paragraphStyle.firstLineHeadIndent = 0
        paragraphStyle.headIndent = 15
        
        return paragraphStyle
    }
    
    func countTotalPoints(component: Component) -> String{
        var points: Int = 0
        
        guard component.activities != nil else {
            return "Nog geen activiteiten"
        }
        for a in component.activities! {
            points += a.points!
        }
        
        return String(points)
    }
    
    @IBAction func addComponent(_ sender: Any) {
        let successAlert = UIAlertController(title: "Gelukt", message: "Component toegevoegd!", preferredStyle: .alert)
        successAlert.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (alert) in
            self.performSegueToReturnBack()
        }))
        
        let addComponentAlert = UIAlertController(title: "Component toevoegen?", message:
            "Wil je '\(component!.name!)' toevoegen?", preferredStyle: UIAlertControllerStyle.alert)
        addComponentAlert.addAction(UIAlertAction(title: "Ja", style: UIAlertActionStyle.default, handler: { (action) in
            startActivityIndicator(atVC: self, withView: self.view, andIndicatorBGView: self.activityBG)
            
            clientService.enrollClientInComponent(clientId: CurrentUser.instance.id!, componentId: self.component!.id!, onSuccess: {
                
                // Pass to previous VC which component is added
                self.delegate?.passAddedComponent(component: self.component!, index: self.componentIndex!)
                futureplanChanged = true
                
                self.present(successAlert, animated: true)
                stopActivityIndicator(withIndicatorBGView: self.activityBG)
            }, onFailure:  {
                simpleAlert(atVC: self,
                            withTitle: "Er is iets fout gegaan.",
                            andMessage: "Het is niet gelukt het component toe te voegen.")
                stopActivityIndicator(withIndicatorBGView: self.activityBG)
            })
        }))
        
        addComponentAlert.addAction(UIAlertAction(title: "Nee", style: UIAlertActionStyle.cancel, handler: nil))
        
        self.present(addComponentAlert, animated: true, completion: nil)
    }
}
