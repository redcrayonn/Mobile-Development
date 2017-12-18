//
//  HomescreenViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON

class HomescreenViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    @IBOutlet weak var collectionView: UICollectionView!
    var buildingblocks: [Buildingblock] = []
    var components: [Component] = []
    var activities: [Activity] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Make collectionView background clear, otherwise it's black
        self.collectionView.backgroundColor = UIColor.clear
        
        // Get the client's buildingblocks, components and activities
        getClientFuturePlan()
    }
    
    // Get all buildingblocks, components and activites a client is working on
    func getClientFuturePlan() {
        buildingblockService.getBuildingblocks(onSuccess: { (results) in
//            print(results)
            self.buildingblocks = results
        }) {
            print("failed to fetch buildingblocks")
        }
        
        clientService.getFutureplan(ofClient: CurrentUser.instance.id!)
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingblocks.count
    }
    
    // Load the buildingblocks in a collectionView
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingblockCell", for: indexPath) as! BuildingblockCell
        
        cell.title.text = buildingblocks[indexPath.row].name!
        cell.title.numberOfLines = 2;
        cell.title.adjustsFontSizeToFitWidth = true;
        cell.buildingblock = buildingblocks[indexPath.row]
        
        // Hide cell unless there is a component for it.
        cell.isHidden = true
        for component in components {
            if cell.buildingblock?.name == component.buildingblockId {
                cell.isHidden = false
                break
            }
        }
        
        // Style the cell with cornerradius
        cell.mainBackground.layer.cornerRadius = 6
        cell.mainBackground.layer.masksToBounds = true
        
        cell.shadowLayer.layer.backgroundColor = UIColor.clear.cgColor
        
        return cell
    }
    
    @IBAction func logoutButton(_ sender: Any) {
        // Do some logout actions
        
        let storyBoard : UIStoryboard = UIStoryboard(name: "Client", bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(
            withIdentifier: "SBLogin") as UIViewController
        self.present(nextViewController, animated:true, completion:nil)
    }
    
    // Prepare navigation segue to ComponentViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        var buildingblockComponents: [Component] = []
        
        if let destinationViewController = segue.destination as? ComponentViewController {
            if let cell = sender as? BuildingblockCell {
                
                // Only send the components for the clicked buildingblock
                for component in components {
                    if cell.buildingblock?.name == component.buildingblockId {
                        buildingblockComponents.append(component)
                    }
                }
                destinationViewController.components = buildingblockComponents
                destinationViewController.buildingblock = cell.buildingblock
                destinationViewController.buildingblockImage = cell.buildingblockImage
            }
        }
    }
}
