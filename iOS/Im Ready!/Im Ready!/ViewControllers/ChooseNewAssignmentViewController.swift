//
//  ChooseNewAssignmentViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 28/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ChooseNewAssignmentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    @IBOutlet weak var collectionView: UICollectionView!
    
    var buildingblocks: [Buildingblock] = []
    var t_count:Int = 0
    var lastCell: BuildingblockStackViewCell = BuildingblockStackViewCell()
    var button_tag:Int = -1

    override func viewDidLoad() {
        super.viewDidLoad()
        
        getBuildingblocks()
        
        collectionView = UICollectionView(frame: view.frame)
        collectionView?.layer.frame.size.height = view.frame.height * 1.5
        collectionView?.frame.origin.y += 125
//        collectionView?.register(UINib(nibName: "StackViewCell", bundle: nil), forCellReuseIdentifier: "StackViewCell")
        collectionView?.delegate = self
        collectionView?.dataSource = self
        collectionView?.allowsSelection = false
        view.addSubview(collectionView)

    }
    
    func getBuildingblocks() {
        startActivityIndicator(atVC: self, withView: view, andIndicatorBGView: nil)
        buildingblockService.getBuildingblocks(onSuccess: { (results) in
            print(results)
            
            self.buildingblocks = results
            self.collectionView?.reloadData()
            stopActivityIndicator(withIndicatorBGView: nil)
        }) {
            simpleAlert(atVC: self, withTitle: "Er is iets fout gegaan", andMessage: "Kon de bouwblokken niet ophalen")
            print("failed to retrieve buildingblocks")
            stopActivityIndicator(withIndicatorBGView: nil)
        }
        
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return buildingblocks.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "buildingblockCell", for: indexPath) as! BuildingblockCell

        cell.layer.backgroundColor = UIColor(hexString: "F16122").cgColor
        cell.buildingblockImage.image = UIImage(
            named: "\(buildingblocks[indexPath.row].type!)")
        cell.title.text = buildingblocks[indexPath.row].name
        
        return cell
    }
    
    func cellOpened(sender:UIButton) {
        self.collectionView.beginUpdates()
        
        let previousCellTag = button_tag
        
        if lastCell.cellExists {
            self.lastCell.animate(duration: 0.2, c: {
                self.view.layoutIfNeeded()
            })
            
            if sender.tag == button_tag {
                button_tag = -1
                lastCell = BuildingblockStackViewCell()
            }
        }
        
        if sender.tag != previousCellTag {
            button_tag = sender.tag
            
            lastCell = tableView.cellForRow(at: IndexPath(row: button_tag, section: 0)) as! BuildingblockStackViewCell
            self.lastCell.animate(duration: 0.2, c: {
                self.view.layoutIfNeeded()
            })
            
        }
        self.tableView.endUpdates()
    }

}
