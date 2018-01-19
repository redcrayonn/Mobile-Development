//
//  ComponentTableViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 27/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import Reachability

public var activityAnswered: Bool = false

protocol UpdateFutureplanDelegate: class {
    func updateFutureplan(withResults: FutureplanResult)
}

class ClientComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    var buildingblock: ClientBuildingblock!
    var components: [ClientComponent]!
    var buildingblockImage: UIImageView!
    weak var delegate: UpdateFutureplanDelegate?
    let reachability = Reachability()!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblock?.name
    }
    
    // If an activity got an answer, we have to reload the whole futureplan again
    // We cannot get the seperate activities for a client.
    override func viewWillAppear(_ animated: Bool) {
        if activityAnswered == true {
            reachability.whenReachable = { _ in
                // Fetch the new futureplan async
                DispatchQueue.main.async {
                    clientService.getFutureplan(ofClient: CurrentUser.instance.id!,
                                                onSuccess: { (result) -> Void in
                                                    self.components = []
                                                    self.delegate?.updateFutureplan(withResults: result)
                                                    for b in (result.buildingblocks)! {
                                                        for c in b.components! {
                                                            self.components.append(c)
                                                        }
                                                    }
                                                    
                                                    self.collectionView.reloadData()
                    }, onFailure: {
                        print("something went wrong")
                    })
                }
                
                activityAnswered = false                
            }
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return (components?.count)!
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComponentCell", for: indexPath) as! ClientComponentCollectionViewCell
        
        cell.nameLbl?.text = components[indexPath.row].name
        cell.component = components[indexPath.row]        
        cell.buildingblockImageView.image = UIImage(named: "\(self.buildingblock.type!)")
        
        let numberOfActivities = components[indexPath.row].activities!.count
        var activityString: String
        if numberOfActivities == 1 {
            activityString = "activiteit"
        } else {
            activityString = "activiteiten"
        }
        
        cell.activitiesLbl.text = "\(numberOfActivities) \(activityString)"
        
        cell.styleCell()        
        
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let destinationViewController = segue.destination as? ClientActivityViewController {
            
            if let cell = sender as? ClientComponentCollectionViewCell {
                destinationViewController.component = cell.component
                destinationViewController.activities = (cell.component?.activities)!
            }
        }
    }
}
