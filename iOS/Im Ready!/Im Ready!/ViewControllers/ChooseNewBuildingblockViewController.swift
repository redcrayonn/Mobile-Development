//
//  ChooseNewAssignmentViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 28/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ChooseNewBuildingblockViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {
    
    var collectionView: UICollectionView!
    let cellIdentifier = "BuildingblockCell"
    var buildingblocks: [Buildingblock] = []
    
    var clientBuildingblocks: [ClientBuildingblock] = []
    var clientComponents: [String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        print(clientBuildingblocks)
        
        // Create a list of all the components the client is enrolled in
        for buildingblock in clientBuildingblocks {
            if buildingblock.type! != BlockType.ADD {
                for component in buildingblock.components! {
                    clientComponents.append(component.name!)
                }
            }
        }
    }
    
    // Check if a client is already working on a component
    // If a client is already working on it, it should nog show in the list.
    func compareComponents(clientComponents: [String], buildingblockComponents: [Component]) -> [Component] {
        var remainingComponents: [Component] = []
        
        for bc in buildingblockComponents {
            print(bc.name!)
            if !clientComponents.contains(bc.name!){
                remainingComponents.append(bc)
            }
        }
        
        return remainingComponents
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingblocks.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: cellIdentifier, for: indexPath as IndexPath) as! BuildingblockCollectionViewCell
        
        let remainingComponents: [Component] = self.compareComponents(clientComponents: clientComponents, buildingblockComponents: buildingblocks[indexPath.row].components!)
        
        cell.buildingblockImage.image = UIImage(named: "\(buildingblocks[indexPath.row].type!)")
        cell.buildingblockName.text = buildingblocks[indexPath.row].name
        cell.components = remainingComponents
        
        let numberOfComponents = remainingComponents.count
        var componentString: String
        if numberOfComponents == 1 {
            componentString = "component"   
        } else {
            componentString = "componenten"
        }
        
        cell.componentsLbl.text = "\(numberOfComponents) \(componentString)"
        
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ChooseNewComponentViewController {
            if let cell = sender as? BuildingblockCollectionViewCell {
                destinationViewController.components = cell.components
                destinationViewController.buildingblockTitle = cell.buildingblockName.text
            }
        }
    }
}

