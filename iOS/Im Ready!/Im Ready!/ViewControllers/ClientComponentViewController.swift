//
//  ComponentTableViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 27/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

public var activityAnswered: Bool = false
protocol UpdateFutureplanDelegate: class {
    func updateFutureplan(withResults: FutureplanResult)
}

class ClientComponentViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    
    var collectionView: UICollectionView!
    var buildingblock: ClientBuildingblock!
    var components: [ClientComponent]!
    var buildingblockImage: UIImageView!
    
    weak var delegate: UpdateFutureplanDelegate?
    
    let screenWidth = UIScreen.main.bounds.width
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblock?.name
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if activityAnswered == true {
            
            // Fetch the new futureplan async
            DispatchQueue.main.async {
                clientService.getFutureplan(ofClient: CurrentUser.instance.id!,
                                            onSuccess: { (result) -> Void in
                                                self.delegate?.updateFutureplan(withResults: result)
                }, onFailure: {
                    print("something went wrong")
                })
            }
            
            activityAnswered = false
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return (components?.count)!
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ComponentCell", for: indexPath) as! ClientComponentCollectionViewCell
        
        cell.name?.text = components[indexPath.row].name
        cell.component = components[indexPath.row]
        
        cell.layer.backgroundColor = UIColor.clear.cgColor
        
        cell.cellBG.layer.backgroundColor = UIColor.white.cgColor
        cell.cellBG.layer.cornerRadius = 5
        cell.cellBG.layer.borderWidth = 1.0
        cell.cellBG.layer.borderColor = UIColor.clear.cgColor
        
        cell.layer.cornerRadius = 6.0
        
        cell.layer.masksToBounds = false
        cell.layer.shadowColor = UIColor.black.cgColor
        cell.layer.shadowOpacity = 0.6
        cell.layer.shadowOffset = CGSize(width: -1, height: 1)
        cell.layer.shadowRadius = 3
        
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
