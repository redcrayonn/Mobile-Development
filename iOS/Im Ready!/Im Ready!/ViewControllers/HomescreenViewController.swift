//
//  HomescreenViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import ChameleonFramework

public var futureplanChanged: Bool = false
//public var buildingblocks: [Buildingblock] = []

class HomescreenViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    @IBOutlet weak var collectionView: UICollectionView!
    
    var clientBuildingblocks: [ClientBuildingblock] = []
    let addBuildingblockBlockName: String = "Toevoegen"
    var buildingblocks: [Buildingblock] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Make collectionView background clear, otherwise it's black
        self.collectionView.backgroundColor = UIColor.clear
        
        // Get the client's buildingblocks, components and activities
        getClientFuturePlan()
        
        // Already fetch all buildingblocks for adding to futureplan
        getBuildingblocks()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        // If a client added new components, the futurplan has to be reloaded
        if futureplanChanged{
            getClientFuturePlan()
            futureplanChanged = false
        }
    }
    
    func getBuildingblocks() {
        startActivityIndicator(atVC: self, withView: view, andIndicatorBGView: nil)
        
        buildingblockService.getBuildingblocks(onSuccess: { (results) in
            self.buildingblocks = results
            stopActivityIndicator(withIndicatorBGView: nil)
        }) {
            simpleAlert(atVC: self, withTitle: "Er is iets fout gegaan", andMessage: "Kon de bouwblokken niet ophalen")
            print("failed to retrieve buildingblocks")
            stopActivityIndicator(withIndicatorBGView: nil)
        }
    }
    
    // Get all buildingblocks, components and activites a client is working on
    func getClientFuturePlan() {
        startActivityIndicator(atVC: self, withView: view, andIndicatorBGView: nil)
        clientService.getFutureplan(ofClient: CurrentUser.instance.id!,
                                    onSuccess: { (results) in
                                        self.clientBuildingblocks = results.buildingblocks!

                                        // Add the last buildingblock where client can add new future goals
                                        self.clientBuildingblocks.append(ClientBuildingblock(
                                            type: BlockType.ADD,
                                            name: self.addBuildingblockBlockName))
                                        self.collectionView.reloadData()
                                        stopActivityIndicator(withIndicatorBGView: nil)
        }) {
            print("failed to retrieve futureplan")
            simpleAlert(atVC: self, withTitle: "Er is iets fout gegaan", andMessage: "Kon bouwblokken niet ophalen.")
            stopActivityIndicator(withIndicatorBGView: nil)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return clientBuildingblocks.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        // If it's the last cell in the array, add the "ADD" block.
        if indexPath.row == (clientBuildingblocks.count - 1) {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "addBuildingblockCell", for: indexPath) as! AddBuildingblockCollectionViewCell
            
            // The images in the Assets folder have the same name as the Buildingblocks, keep that in mind
            cell.buildingblockImage.image = UIImage(
                named: "\(clientBuildingblocks[indexPath.row].type!)")
            cell.title.text = clientBuildingblocks[indexPath.row].name
            cell.title.numberOfLines = 2
            cell.title.adjustsFontSizeToFitWidth = true
            
            // Set orange background and cornerradius
            cell.mainBackground.layer.backgroundColor = UIColor(hexString: "F16122").cgColor
            cell.mainBackground.layer.cornerRadius = 6
            cell.mainBackground.layer.masksToBounds = true
            
            return cell
            
        } else {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingblockCell", for: indexPath) as! BuildingblockCell
            
            // The images in the Assets folder have the same name as the Buildingblocks, keep that in mind
            cell.buildingblockImage.image = UIImage(
                named: "\(clientBuildingblocks[indexPath.row].type!)")
            cell.title.text = clientBuildingblocks[indexPath.row].name
            cell.buildingblock = clientBuildingblocks[indexPath.row]
            cell.title.numberOfLines = 2
            cell.title.adjustsFontSizeToFitWidth = true
            
            // Style the cell
            cell.mainBackground.layer.cornerRadius = 6
            cell.mainBackground.layer.masksToBounds = true
            cell.shadowLayer.layer.backgroundColor = UIColor.clear.cgColor
            
            return cell
        }
    }
    
    // Prepare navigation segue to ComponentViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ClientComponentViewController {
            if let cell = sender as? BuildingblockCell {
                if cell.buildingblock != nil {
                    destinationViewController.components = cell.buildingblock?.components
                    destinationViewController.buildingblock = cell.buildingblock
                    destinationViewController.buildingblockImage = cell.buildingblockImage
                }
            }
        } else if let destinationViewController = segue.destination as? ChooseNewBuildingblockViewController {
            // Check if the array is not empty before departing to next view
            if !self.buildingblocks.isEmpty {
                destinationViewController.buildingblocks = self.buildingblocks
                destinationViewController.clientBuildingblocks = self.clientBuildingblocks
            }
        }
    }
    
    @IBAction func logoutButton(_ sender: Any) {
        CurrentUser.instance.logout()
        
        let storyBoard : UIStoryboard = UIStoryboard(name: "Client", bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(
            withIdentifier: "SBLogin") as UIViewController
        self.present(nextViewController, animated:true, completion:nil)
    }
}
