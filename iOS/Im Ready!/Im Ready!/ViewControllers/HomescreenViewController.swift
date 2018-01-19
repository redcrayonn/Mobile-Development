//
//  HomescreenViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import ChameleonFramework
import NVActivityIndicatorView
import Reachability

public var futureplanChanged: Bool = false

class HomescreenViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource, UpdateFutureplanDelegate, NVActivityIndicatorViewable {
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    var clientBuildingblocks: [ClientBuildingblock] = []
    let addBuildingblockBlockName: String = "Toevoegen"
    var buildingblocks: [Buildingblock] = []
    var activities: [ClientActivity] = []
    let reachability = Reachability()!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Make collectionView background clear, otherwise it's black
        self.collectionView.backgroundColor = UIColor.clear
        
        // Get the client's buildingblocks, components and activities
        getClientFuturePlan()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        // If a client added new components, the futurplan has to be reloaded
        if futureplanChanged{
            getClientFuturePlan()
            futureplanChanged = false
        }
        
        NotificationCenter.default.addObserver(self, selector: #selector(reachabilityChanged), name: .reachabilityChanged, object: reachability)
        do{
            try reachability.startNotifier()
        }catch{
            print("could not start reachability notifier")
        }
        
    }
    
    func getBuildingblocks() {
        guard reachability.connection != .none else {
            simpleAlert(atVC: self, withTitle: "Geen internetverbinding", andMessage: "Kan de bouwblokken niet ophalen zonder internetverbinding")
            
            return
        }
        
        startAnimating(CGSize(width: 30.0, height: 30.0), message: "Toekomstplan wordt opgehaald", type: .lineSpinFadeLoader)
        
        buildingblockService.getBuildingblocks(onSuccess: { (results) in
            self.buildingblocks = results
            if !self.clientBuildingblocks.isEmpty {
                self.stopAnimating()
            }
        }) {
            simpleAlert(atVC: self, withTitle: "Er is iets fout gegaan", andMessage: "Kon de bouwblokken niet ophalen")
            print("failed to retrieve buildingblocks")
            self.stopAnimating()
        }
    }
    
    // Get all buildingblocks, components and activites a client is working on
    func getClientFuturePlan() {
        guard reachability.connection != .none else {
            simpleAlert(atVC: self, withTitle: "Geen internetverbinding", andMessage: "Kan het toekomstplan niet ophalen zonder internetverbinding")
            
            return
        }
        
        startAnimating(CGSize(width: 30.0, height: 30.0), message: "Toekomstplan wordt opgehaald", type: .lineSpinFadeLoader)
        
        // Already fetch all buildingblocks for adding to futureplan
        getBuildingblocks()
        
        clientService.getFutureplan(ofClient: CurrentUser.instance.id!,
                                    onSuccess: { (results) in
                                        
                                        self.createFutureplan(forResults: results)
                                        if !self.buildingblocks.isEmpty {
                                            self.stopAnimating()
                                        }
                                        
        }) {
            print("failed to retrieve futureplan")
            simpleAlert(atVC: self, withTitle: "Er is iets fout gegaan", andMessage: "Kon bouwblokken niet ophalen.")
            self.stopAnimating()
        }
    }
    
    func createFutureplan(forResults results: FutureplanResult) {
        self.clientBuildingblocks = results.buildingblocks!
        
        // Add the last buildingblock where client can add new future goals
        self.clientBuildingblocks.append(ClientBuildingblock(
            type: BlockType.ADD,
            name: self.addBuildingblockBlockName))
        self.collectionView.reloadData()
    }
    
    func countActivitiesThatNeedWork(forBuildingblock buildingblock: ClientBuildingblock) -> Int {
        var count = 0
        
        for c in buildingblock.components! {
            for a in c.activities! {
                // When an activity has the status 0 (ONGOING) or status 1 (PENDING), add them to the list
                if a.status! != 2 {
                    count += 1
                }
            }
        }
        
        return count
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
            cell.layer.cornerRadius = 6
            cell.layer.masksToBounds = true
            
            let numberOfActivities = countActivitiesThatNeedWork(forBuildingblock: clientBuildingblocks[indexPath.row])
            if numberOfActivities > 0 {
                cell.activitiesLbl.text = String(numberOfActivities)
            } else {
                cell.activitiesLbl.isHidden = true
            }
            
            return cell
        }
    }
    
    func updateFutureplan(withResults results: FutureplanResult) {
        createFutureplan(forResults: results)
    }
    
    // Prepare navigation segue to ComponentViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        // First check which collectionViewCell is clicked
        if let destinationViewController = segue.destination as? ClientComponentViewController {
            destinationViewController.delegate = self
            
            if let cell = sender as? BuildingblockCell {
                if cell.buildingblock != nil {
                    destinationViewController.components = cell.buildingblock?.components
                    destinationViewController.buildingblock = cell.buildingblock
                    destinationViewController.buildingblockImage = cell.buildingblockImage
                }
            }
            
            // When the "ADD" block is clicked we go this way
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
        self.dismiss(animated: true, completion: nil)        
    }
    
    @objc func reachabilityChanged(note: Notification) {
        let reachability = note.object as! Reachability
        
        reachability.whenUnreachable = { _ in
            self.stopAnimating()
            simpleAlert(atVC: self, withTitle: "Geen internetverbinding", andMessage: "Kan data niet ophalen")
        }
        
        reachability.whenReachable = { _ in
            self.getClientFuturePlan()
        }
    }
}
