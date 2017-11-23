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
        self.collectionView.backgroundColor = UIColor.clear
        
        buildingBlocks = buildingblockService.getMockBuildingblocks()
        components = componentService.getMockComponents()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingBlocks.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingblockCell", for: indexPath) as! BuildingblockCell
        cell.buildingblockImage.image = UIImage(named: buildingBlocks[indexPath.row].imageURL!)
        cell.title.text = buildingBlocks[indexPath.row].name!
        cell.buildingblock = buildingBlocks[indexPath.row]
        
        return cell
    }
    
    // Set collectionviewcell layout
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        // Compute the dimension of a cell for an NxN layout with space S between
        // cells.  Take the collection view's width, subtract (N-1)*S points for
        // the spaces between the cells, and then divide by N to find the final
        // dimension for the cell's width and height.
        
        let cellsAcross: CGFloat = 3
        let spaceBetweenCells: CGFloat = 1
        let dim = (collectionView.bounds.width - (cellsAcross - 1) * spaceBetweenCells) / cellsAcross
        return CGSize(width: dim, height: dim)
    }
    
    @IBAction func logoutButton(_ sender: Any) {
        let storyBoard : UIStoryboard = UIStoryboard(name: "Client", bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(
            withIdentifier: "SBLogin") as UIViewController
        self.present(nextViewController, animated:true, completion:nil)
    }
    
    // Prepare navigation segue
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? BuildingblockDetailViewController {
            if let cell = sender as? BuildingblockCell {
               destinationViewController.buildingblock = cell.buildingblock
            }
        }
    }
}
