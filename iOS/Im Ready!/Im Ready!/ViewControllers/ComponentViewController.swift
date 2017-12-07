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
    var components: [Component]?
    var buildingblockImage: UIImageView?
    
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
        
        return cell
    }
}
