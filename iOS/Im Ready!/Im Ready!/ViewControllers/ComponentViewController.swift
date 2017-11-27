//
//  ComponentTableViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 27/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ComponentViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    @IBOutlet weak var componentTableView: UITableView!
    
    var buildingblock: Buildingblock?
    var components: [Component]?
    var activities: [Activity]?
    
    let cellSpacingHeight: CGFloat = 15
    
    override func viewDidLoad() {
        super.viewDidLoad()
        componentTableView.delegate = self
        componentTableView.dataSource = self
        
        self.title = buildingblock?.name
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return (components?.count)!
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return cellSpacingHeight
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ComponentCell", for: indexPath) as! ComponentTableViewCell
        cell.fill(withComponent: components![indexPath.row])
        
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ActivityViewController {
            if let cell = sender as? ComponentTableViewCell {
                destinationViewController.component = cell.component
            }
        }
    }
    
}
