//
//  ComponentTableViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 27/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ClientComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    var collectionView: UICollectionView!
    var buildingblock: ClientBuildingblock!
    var components: [ClientComponent]!
    var buildingblockImage: UIImageView!
    
    let screenWidth = UIScreen.main.bounds.width

    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblock?.name
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return (components?.count)!
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComponentCell", for: indexPath) as! ClientComponentCollectionViewCell
        
        cell.name?.text = components[indexPath.row].name
        cell.component = components[indexPath.row]
        
        cell.layer.backgroundColor = UIColor.clear.cgColor
        
        cell.cellBG.layer.backgroundColor = UIColor.white.cgColor
        cell.cellBG.layer.cornerRadius = 5
        cell.cellBG.layer.borderWidth = 1.0
        cell.cellBG.layer.borderColor = UIColor.clear.cgColor
        
        cell.layer.cornerRadius = 6.0
        
        cell.layer.masksToBounds = false
        cell.layer.shadowColor = UIColor.black.cgColor
        cell.layer.shadowOpacity = 0.6
        cell.layer.shadowOffset = CGSize(width: -1, height: 1)
        cell.layer.shadowRadius = 3
        
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ClientActivityViewController {
            if let cell = sender as? ClientComponentCollectionViewCell {
                destinationViewController.component = cell.component
                destinationViewController.activities = (cell.component?.activities)!
            }
        }
    }
}
