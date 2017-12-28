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
    
    var buildingblocks: [ClientBuildingblock] = []
    let addBuildingblockBlockName: String = "Toevoegen"
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Make collectionView background clear, otherwise it's black
        self.collectionView.backgroundColor = UIColor.clear
        
        // Get the client's buildingblocks, components and activities
        getClientFuturePlan()
    }
    
    // Get all buildingblocks, components and activites a client is working on
    func getClientFuturePlan() {
        startActivityIndicator(atVC: self, withView: view, andIndicatorBGView: nil)
        clientService.getFutureplan(ofClient: CurrentUser.instance.id!,
                                    onSuccess: { (results) in
                                        self.buildingblocks = results.buildingblocks!
                                        
                                        // Add the last buildingblock where you can select new future goals
                                        self.buildingblocks.append(ClientBuildingblock(
                                            type: BlockType.ADD,
                                            name: self.addBuildingblockBlockName))
                                        
                                        self.collectionView.reloadData()
                                        stopActivityIndicator(withIndicatorBGView: nil)
        }) {
            print("failed to retrieve futureplan")
            stopActivityIndicator(withIndicatorBGView: nil)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingblocks.count
    }
    
    // Load the buildingblocks in a collectionView
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        if indexPath.row == (buildingblocks.count - 1) {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "addBuildingblockCell", for: indexPath) as! AddBuildingblockCollectionViewCell
            cell.title.text = buildingblocks[indexPath.row].name
            cell.buildingblockImage.image = UIImage(
                named: "\(buildingblocks[indexPath.row].type!)")
            
            cell.title.numberOfLines = 2
            cell.title.adjustsFontSizeToFitWidth = true

            // Set orange background and cornerradius
            cell.mainBackground.layer.backgroundColor = UIColor(hexString: "F16122").cgColor
            cell.mainBackground.layer.cornerRadius = 6
            cell.mainBackground.layer.masksToBounds = true
            
            return cell
            
        } else {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingblockCell", for: indexPath) as! BuildingblockCell
            
            cell.title.text = buildingblocks[indexPath.row].name
            cell.buildingblockImage.image = UIImage(
                named: "\(buildingblocks[indexPath.row].type!)")
            cell.buildingblock = buildingblocks[indexPath.row]
            cell.title.numberOfLines = 2
            cell.title.adjustsFontSizeToFitWidth = true
            
            // Style the cell
            cell.mainBackground.layer.cornerRadius = 6
            cell.mainBackground.layer.masksToBounds = true
            cell.shadowLayer.layer.backgroundColor = UIColor.clear.cgColor
            
            print(indexPath.row)
            print(buildingblocks.count)
                        
            return cell
        }
    }
    
    // Prepare navigation segue to ComponentViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ComponentViewController {
            if let cell = sender as? BuildingblockCell {
                if cell.buildingblock != nil {
                    destinationViewController.components = cell.buildingblock?.components
                    destinationViewController.buildingblock = cell.buildingblock
                    destinationViewController.buildingblockImage = cell.buildingblockImage
                }
            }
        }
    }
    
    @IBAction func logoutButton(_ sender: Any) {
        CurrentUser.instance.access_token = nil
        CurrentUser.instance.id = nil
        CurrentUser.instance.username = nil
        CurrentUser.instance.user_type = nil
        
        let storyBoard : UIStoryboard = UIStoryboard(name: "Client", bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(
            withIdentifier: "SBLogin") as UIViewController
        self.present(nextViewController, animated:true, completion:nil)
    }
}
