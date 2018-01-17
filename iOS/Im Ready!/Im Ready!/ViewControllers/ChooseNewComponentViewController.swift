//
//  ChooseNewComponentViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 05/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit

//protocol ComponentDelegate: class {
//    func passAddedComponent(component: Component)
//}

class ChooseNewComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, ComponentDelegate {
    
    @IBOutlet weak var collectionView: UICollectionView!
//    weak var delegate: ComponentDelegate?
    
    var components: [Component] = []
    var buildingblockTitle: String?
    var backgroundColor: UIColor?
    var itemCount: Int = 0
    weak var delegate: ComponentDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblockTitle
        collectionView.frame.origin.y += 200

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
        cell.component = components[indexPath.row]
        cell.index = indexPath
        
        cell.styleCell()
        
        return cell
    }
    
    func passAddedComponent(component: Component, index: IndexPath) {
        // Delete the item from the collectionView
        self.collectionView.performBatchUpdates({
            self.collectionView.deleteItems(at: [index])
            // Manually substract 1 to satisfy the collectionView
            self.itemCount -= 1
        }) { (finished) in
            self.collectionView.reloadItems(at: self.collectionView.indexPathsForVisibleItems)
        }
        
        self.delegate?.passAddedComponent(component: component, index: index)
        
        if itemCount == 0 {
            self.performSegueToReturnBack()
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ChooseNewComponentDetailViewController {
            destinationViewController.delegate = self
            
            if let cell = sender as? ComponentCollectionViewCell {
                destinationViewController.component = cell.component
                destinationViewController.componentIndex = cell.index
            }
        }
    }
}
