//
//  ChooseNewComponentDetailViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit
import Reachability
import NVActivityIndicatorView

protocol ComponentDelegate: class {
    func passAddedComponent(component: Component, index: IndexPath)
}

class ChooseNewComponentDetailViewController: UIViewController, NVActivityIndicatorViewable {
    
    @IBOutlet weak var descriptionLbl: UILabel!
    @IBOutlet weak var activitiesLbl: UILabel!
    @IBOutlet weak var pointsLbl: UILabel!
    @IBOutlet weak var addBtn: UIButton!
    
    var component: Component?
    var componentIndex: IndexPath?
    weak var delegate: ComponentDelegate?
    let reachability = Reachability()!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = component!.name
        descriptionLbl.text = component?.description
        pointsLbl.text = countTotalPoints(component: component!)
        
        addBtn.layer.masksToBounds = false
        addBtn.layer.shadowColor = UIColor.black.cgColor
        addBtn.layer.shadowOpacity = 0.6
        addBtn.layer.shadowOffset = CGSize(width: 0, height: 0)
        addBtn.layer.shadowRadius = 4

        makeBulletListOfActivities(component: component!)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        NotificationCenter.default.addObserver(self, selector: #selector(reachabilityChanged), name: .reachabilityChanged, object: reachability)
        do{
            try reachability.startNotifier()
        }catch{
            print("could not start reachability notifier")
        }
    }
    
    func makeBulletListOfActivities(component: Component) {
        var activities: [String] = []
        
        guard !(component.activities?.isEmpty)! else {
            activitiesLbl.text = "Nog geen activiteiten"
            return
        }
        
        for a in component.activities! {
            activities.append(a.name!)
        }
        
        let attributesDictionary = [NSAttributedStringKey.font : activitiesLbl.font]
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
        
        activitiesLbl.attributedText = fullAttributedString
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
        
        guard (component.activities?.isEmpty)! else {
            return "Nog geen activiteiten"
        }
        
        for a in component.activities! {
            points += a.points!
        }
        
        return String(points)
    }
    
    @IBAction func addComponent(_ sender: Any) {
        guard reachability.connection != .none else {
            simpleAlert(atVC: self, withTitle: "Geen internetverbinding", andMessage: "Kan component niet toevoegen zonder internetverbiding")
            
            return
        }
        
        let successAlert = createSuccessAlert()
        
        let addComponentAlert = UIAlertController(title: "Component toevoegen?", message:
            "Wil je '\(component!.name!)' toevoegen?", preferredStyle: UIAlertControllerStyle.alert)
        addComponentAlert.addAction(UIAlertAction(title: "Ja", style: UIAlertActionStyle.default, handler: { _ in
            self.startAnimating(CGSize(width: 30.0, height: 30.0), type: .lineSpinFadeLoader)
            
            clientService.enrollClientInComponent(clientId: CurrentUser.instance.id!, componentId: self.component!.id!, onSuccess: {
                
                // Pass to previous VC which component is added
                self.delegate?.passAddedComponent(component: self.component!, index: self.componentIndex!)
                futureplanChanged = true
                
                self.present(successAlert, animated: true)
                self.stopAnimating()
            }, onFailure:  {
                simpleAlert(atVC: self,
                            withTitle: "Er is iets fout gegaan.",
                            andMessage: "Het is niet gelukt het component toe te voegen.")
                self.stopAnimating()
            })
        }))
        
        addComponentAlert.addAction(UIAlertAction(title: "Nee", style: UIAlertActionStyle.cancel, handler: nil))
        
        self.present(addComponentAlert, animated: true, completion: nil)
    }
    
    func createSuccessAlert() -> UIAlertController {
        let successAlert = UIAlertController(title: "Gelukt", message: "Component toegevoegd!", preferredStyle: .alert)
        successAlert.addAction(UIAlertAction(title: "Ok", style: .default, handler: { (alert) in
            self.performSegueToReturnBack()
        }))
        
        return successAlert
    }
    
    @objc func reachabilityChanged(note: Notification) {
        let reachability = note.object as! Reachability
        
        reachability.whenUnreachable = { _ in
            self.stopAnimating()
            simpleAlert(atVC: self, withTitle: "Geen internetverbinding", andMessage: "")
        }
        
        reachability.whenReachable = { _ in

        }
    }
}
