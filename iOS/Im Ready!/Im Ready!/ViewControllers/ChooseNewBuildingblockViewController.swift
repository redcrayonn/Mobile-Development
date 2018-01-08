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
    
    override func viewDidLoad() {
        super.viewDidLoad()        
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingblocks.count
    }
        
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: cellIdentifier, for: indexPath as IndexPath) as! BuildingblockCollectionViewCell
        
        cell.buildingblockImage.image = UIImage(named: "\(buildingblocks[indexPath.row].type!)")
        cell.buildingblockName.text = buildingblocks[indexPath.row].name
        cell.components = buildingblocks[indexPath.row].components!
        
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

