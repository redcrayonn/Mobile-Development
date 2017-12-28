//
//  ComponentTableViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 27/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import ChameleonFramework

class ComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
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
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComponentCell", for: indexPath) as! ComponentCollectionViewCell
        
//        cell.backgroundColor = UIColor(gradientStyle:UIGradientStyle, withFrame:CGRect, andColors:[UIColor])
        cell.name?.text = components[indexPath.row].name
        cell.component = components[indexPath.row]
        
        cell.layer.backgroundColor = UIColor.clear.cgColor
//        cell.layer.shadowColor = UIColor.black.cgColor
//        cell.layer.shadowOffset = CGSize(width: 0, height: 2.0)
//        cell.layer.shadowRadius = 2.0
//        cell.layer.shadowOpacity = 1.0
//        cell.layer.masksToBounds = false
        
        cell.cellBG.layer.backgroundColor = UIColor.white.cgColor
        cell.cellBG.layer.opacity = 0.3
        cell.cellBG.layer.cornerRadius = 6
        cell.cellBG.layer.borderWidth = 1.0
        cell.cellBG.layer.borderColor = UIColor.clear.cgColor
        cell.cellBG.layer.masksToBounds = true
        
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
