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
//    var components: [Component] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Make collectionView background clear, otherwise it's black
        self.collectionView.backgroundColor = UIColor.clear
        
        // Get the client's buildingblocks, components and activities
        getClientFuturePlan()
    }
    
    // Get all buildingblocks, components and activites a client is working on
    func getClientFuturePlan() {
        startActivityIndicator(atVC: self, withView: view, andIndicatorBG: nil)
        clientService.getFutureplan(ofClient: CurrentUser.instance.id!,
                                    onSuccess: { (results) in
                                        self.buildingblocks = results.buildingblocks!
                                        self.collectionView.reloadData()
                                        stopActivityIndicator(withIndicatorBG: nil)
        }) {
            print("failed to retrieve futureplan")
            stopActivityIndicator(withIndicatorBG: nil)
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingblocks.count
    }
    
    // Load the buildingblocks in a collectionView
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingblockCell", for: indexPath) as! BuildingblockCell
        
        cell.title.text = buildingblocks[indexPath.row].name
        cell.title.numberOfLines = 2;
        cell.title.adjustsFontSizeToFitWidth = true;
        cell.buildingblock = buildingblocks[indexPath.row]
        
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
                destinationViewController.components = cell.buildingblock?.components
                destinationViewController.buildingblock = cell.buildingblock
                destinationViewController.buildingblockImage = cell.buildingblockImage
            }
        }
    }
}
