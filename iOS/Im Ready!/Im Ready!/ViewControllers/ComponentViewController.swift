//
//  ComponentTableViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 27/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    var collectionView: UICollectionView!
    var buildingblock: Buildingblock?
    var components: [Component]!
    var buildingblockImage: UIImageView?
    
    let screenWidth = UIScreen.main.bounds.width

    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblock?.name
        components = buildingblock?.components
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return (components?.count)!
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComponentCell", for: indexPath) as! ComponentCollectionViewCell
        
        cell.name?.text = components[indexPath.row].name
        cell.component = components[indexPath.row]
        
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ActivityViewController {
            if let cell = sender as? ComponentCollectionViewCell {
                destinationViewController.component = cell.component
                destinationViewController.activities = (cell.component?.activities)!
            }
        }
    }
}
