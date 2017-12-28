//
//  AddBuildingblockViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 21/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class AddBuildingblockViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet var tableView: UITableView?
    
    var buildingblocks: [Buildingblock] = []
    override func viewDidLoad() {
        super.viewDidLoad()
        
        buildingblockService.getBuildingblocks(onSuccess: { (buildingblocks) in
            self.buildingblocks = buildingblocks
        }) {
            print("failed to retrieve buildingblocks")
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return buildingblocks.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "BuildingblockCell", for: indexPath)
        
        return cell
    }

}
