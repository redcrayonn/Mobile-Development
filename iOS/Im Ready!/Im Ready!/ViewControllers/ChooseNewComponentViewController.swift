//
//  ChooseNewComponentViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 05/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit

protocol ComponentDelegate: class {
    func passAddedComponent(component: Component)
}

class ChooseNewComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    
    @IBOutlet weak var activityIndicatorBG: UIView!
    @IBOutlet weak var collectionView: UICollectionView!
    weak var delegate: ComponentDelegate?
    
    var components: [Component] = []
    var buildingblockTitle: String?
    var backgroundColor: UIColor?
    var itemCount: Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblockTitle
        
        // We need to manually count the items
        // When deleting an item from the collectionview, numberOfItemsInSection is called again and expects a new number
        itemCount = components.count
    }
    
    func collectionViewAllowsEditing(collectionView: UICollectionView) -> Bool {
        return true
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return itemCount
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComponentCell", for: indexPath) as! ComponentCollectionViewCell
        
        cell.componentName.text = components[indexPath.row].name
        cell.backgroundColor = self.backgroundColor
        
        cell.styleCell()
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let addComponentAlert = UIAlertController(title: "Component toevoegen?", message:
            "Wil je '\(components[indexPath.row].name!)' toevoegen?", preferredStyle: UIAlertControllerStyle.alert)
        addComponentAlert.addAction(UIAlertAction(title: "Ja", style: UIAlertActionStyle.default, handler: { (action) in
            
            startActivityIndicator(atVC: self, withView: self.view, andIndicatorBGView: self.activityIndicatorBG)
            
            clientService.enrollClientInComponent(clientId: CurrentUser.instance.id!, componentId: self.components[indexPath.row].id!, onSuccess: {
                
                // Pass to previous VC which component is added
                self.delegate?.passAddedComponent(component: self.components[indexPath.row])
                componentsChanged = true
                
                // Delete the item from the collectionView
                self.collectionView.performBatchUpdates({
                    self.collectionView.deleteItems(at: [indexPath])
                    // Manually substract 1 to satisfy the collectionView
                    self.itemCount -= 1
                }) { (finished) in
                    self.collectionView.reloadItems(at: self.collectionView.indexPathsForVisibleItems)
                }
                
                futureplanChanged = true
                simpleAlert(atVC: self,
                            withTitle: "Gelukt!",
                            andMessage: "Component toegevoegd")
                stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
            }, onFailure:  {
                simpleAlert(atVC: self,
                            withTitle: "Er is iets fout gegaan.",
                            andMessage: "Het is niet gelukt het component toe te voegen.")
                stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
            })
        }))
        
        addComponentAlert.addAction(UIAlertAction(title: "Nee", style: UIAlertActionStyle.cancel, handler: nil))
        
        self.present(addComponentAlert, animated: true, completion: nil)
    }
}
