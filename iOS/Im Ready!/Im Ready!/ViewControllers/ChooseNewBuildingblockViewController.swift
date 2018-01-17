//
//  ChooseNewAssignmentViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 28/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import ChameleonFramework

public var componentsChanged: Bool = false

class ChooseNewBuildingblockViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, ComponentDelegate {
    
    @IBOutlet weak var collectionView: UICollectionView!
    let cellIdentifier = "BuildingblockCell"
    
    var buildingblocks: [Buildingblock] = []
    var clientBuildingblocks: [ClientBuildingblock] = []
    var clientComponents: [String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Bouwblokken"
        
        //        collectionView.frame.origin.y += 200
        
        sortBuildingblocksByNumberOfComponents()
    }
    
    /// Compare the system components with the components of the client
    /// Only return components the client is not enrolled in
    /// Sort them by number of components available
    func sortBuildingblocksByNumberOfComponents() {
        
        // Create new list of component names to compare to original components
        // Cannot compare ClientComponent Object to Component bject
        for b in self.clientBuildingblocks {
            guard b.components != nil else {continue}
            for component in b.components! {
                clientComponents.append(component.name!)
            }
        }
        
        // Compare the client component to the original component
        for b in self.buildingblocks {
            var newComponentList: [Component] = []
            
            for component in b.components! {
                if !clientComponents.contains(component.name!){
                    newComponentList.append(component)
                }
            }
            
            b.components = newComponentList
        }
        
        // Sort descending
        self.buildingblocks = buildingblocks.sorted(by: {($0.components?.count)! > ($1.components?.count)!})
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingblocks.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: cellIdentifier, for: indexPath as IndexPath) as! BuildingblockCollectionViewCell
        
        let components = buildingblocks[indexPath.row].components!
        
        cell.buildingblockImage.image = UIImage(named: "\(buildingblocks[indexPath.row].type!)")
        cell.buildingblockName.text = buildingblocks[indexPath.row].name
        cell.components = components
        cell.backgroundColor = UIColor(hexString: setColor(forIndex: indexPath))
        
        cell.styleCell()
        
        let numberOfComponents = components.count
        var componentString: String
        if numberOfComponents == 1 {
            componentString = "component"   
        } else {
            componentString = "componenten"
        }
        
        cell.componentsLbl.text = "\(numberOfComponents) \(componentString)"
        
        return cell
    }
    
    /// If the client adds a new component to his futureplan, the delegate updates the buildingblockView
    func passAddedComponent(component: Component, index: IndexPath) {
        clientComponents.append(component.name!)
        sortBuildingblocksByNumberOfComponents()
        collectionView.reloadData()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ChooseNewComponentViewController {
            destinationViewController.delegate = self
            
            if let cell = sender as? BuildingblockCollectionViewCell {
                destinationViewController.components = cell.components
                destinationViewController.buildingblockTitle = cell.buildingblockName.text
                destinationViewController.backgroundColor = cell.backgroundColor
            }
        }
    }    
}

