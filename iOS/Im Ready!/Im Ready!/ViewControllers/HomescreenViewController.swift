//
//  HomescreenViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class HomescreenViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    @IBOutlet weak var HomeCollectionView: UICollectionView!
    
    var buildingBlocks: [String] = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                                    "1", "2", "3", "4", "5", "6"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
//        let itemSize = UIScreen.main.bounds.width/3 - 2
//        let layout = UICollectionViewFlowLayout()
//        layout.itemSize = CGSize(width: itemSize, height: itemSize)
//        //        layout.minimumInteritemSpacing = 8
//        //        layout.minimumLineSpacing = 8
//        HomeCollectionView.collectionViewLayout = layout
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingBlocks.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingBlockCell", for: indexPath) as! BuildingBlockCell
        cell.BuildingBlockImage.image = UIImage(named: buildingBlocks[indexPath.row] + ".JPG")
        
        return cell
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAtIndex section: Int) -> UIEdgeInsets {

        let cellCount = CGFloat(buildingBlocks.count)
        
        //If the cell count is zero, there is no point in calculating anything.
        if cellCount > 0 {
//            let flowLayout = collectionViewLayout as! UICollectionViewFlowLayout
//            let cellWidth = flowLayout.itemSize.width + flowLayout.minimumInteritemSpacing
//
//            let totalCellWidth = cellWidth*cellCount + 20.00 * (cellCount-1)
//            let contentWidth = collectionView.frame.size.width - collectionView.contentInset.left - collectionView.contentInset.right
//
//            if (totalCellWidth < contentWidth) {
//                //If the number of cells that exists take up less room than the
//                //collection view width... then there is an actual point to centering them.
//
//                //Calculate the right amount of padding to center the cells.
//                let padding = (contentWidth - totalCellWidth) / 2.0
//                return UIEdgeInsetsMake(0, padding, 0, padding)
//            } else {
//                //Pretty much if the number of cells that exist take up
//                //more room than the actual collectionView width, there is no
//                // point in trying to center them. So we leave the default behavior.
//                return UIEdgeInsetsMake(0, 40, 0, 40)
//            }
        }
//        return UIEdgeInsets.zero
        return UIEdgeInsetsMake(0, 100, 0, 0);

    }
    
    
    @IBAction func logoutButton(_ sender: Any) {
        let storyBoard : UIStoryboard = UIStoryboard(name: "Client", bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(
            withIdentifier: "SBLogin") as UIViewController
        self.present(nextViewController, animated:true, completion:nil)
    }
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
}
