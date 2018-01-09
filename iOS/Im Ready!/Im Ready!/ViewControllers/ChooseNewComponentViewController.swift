//
//  ChooseNewComponentViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 05/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit

class ChooseNewComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    
    var collectionView: UICollectionView!
    
    var components: [Component] = []
    var buildingblockTitle: String?

    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblockTitle
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return components.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComponentCell", for: indexPath) as! ComponentCollectionViewCell
        
        cell.componentName.text = components[indexPath.row].name
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let addComponentAlert = UIAlertController(title: "Component toevoegen?", message:
            "Wil je '\(components[indexPath.row].name!)' toevoegen?", preferredStyle: UIAlertControllerStyle.alert)
        addComponentAlert.addAction(UIAlertAction(title: "Ja", style: UIAlertActionStyle.default, handler: { (action) in
            
            startActivityIndicator(atVC: self, withView: self.view, andIndicatorBGView: nil)
            
            componentService.enrollClientInComponent(clientId: CurrentUser.instance.id!, componentId: self.components[indexPath.row].id!, onSuccess: {
                futureplanChanged = true
                simpleAlert(atVC: self,
                            withTitle: "Gelukt!",
                            andMessage: "Component toegevoegd")
                stopActivityIndicator(withIndicatorBGView: nil)
            }, onFailure:  {
                simpleAlert(atVC: self,
                            withTitle: "Er is iets fout gegaan.",
                            andMessage: "Het is niet gelukt het component toe te voegen.")
                stopActivityIndicator(withIndicatorBGView: nil)
            })
            
        }))
        
        addComponentAlert.addAction(UIAlertAction(title: "Nee", style: UIAlertActionStyle.cancel, handler: nil))
        
        self.present(addComponentAlert, animated: true, completion: nil)
    }
}
