//
//  HomescreenViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import Alamofire

class HomescreenViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    
    @IBOutlet weak var collectionView: UICollectionView!
    var buildingBlocks: [Buildingblock] = []
    var components: [Component] = []
    var activities: [Activity] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Make collectionView background clear, otherwise it's black
        self.collectionView.backgroundColor = UIColor.clear
        
        // Get the client's buildingblocks, components and activities
        getClientDevelopmentPlan()
    }
    
    // Get all buildingblocks, components and activites a client is working on
    func getClientDevelopmentPlan() {
        buildingBlocks = buildingblockService.getMockBuildingblocks()
        //        components = componentService.getMockComponents()
        //        activities = activityService.getMockActivities()
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingBlocks.count
    }
    
    // Load the buildingblocks in a collectionView
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingblockCell", for: indexPath) as! BuildingblockCell
        cell.buildingblockImage.image = UIImage(named: buildingBlocks[indexPath.row].imageURL!)
        cell.title.text = buildingBlocks[indexPath.row].name!
        cell.title.numberOfLines = 2;
        cell.title.adjustsFontSizeToFitWidth = true;
        cell.buildingblock = buildingBlocks[indexPath.row]
        
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
        if let destinationViewController = segue.destination as? ComponentViewController {
            if let cell = sender as? BuildingblockCell {
                destinationViewController.buildingblock = cell.buildingblock
                destinationViewController.buildingblockImage = cell.buildingblockImage
                destinationViewController.components = self.components
//                destinationViewController.activities = self.activities
            }
        }
    }
}
