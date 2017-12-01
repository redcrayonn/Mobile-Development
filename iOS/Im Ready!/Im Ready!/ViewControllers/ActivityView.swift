//
//  ActivityViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 29/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ActivityView: UIView, UITableViewDelegate, UITableViewDataSource {
    var activityTableView: UITableView!
    let screenHeight = UIScreen.main.bounds.height
    let screenWidth = UIScreen.main.bounds.width
    var activities: [Activity] = []
    
    override init(frame: CGRect){
        super.init(frame: frame)
        
        // Initialize the tableview
        activityTableView = UITableView(frame: CGRect(x: 0, y: 0, width: screenWidth*0.5, height: screenHeight))
        activityTableView.separatorStyle = .none
        
        activityTableView.delegate = self
        activityTableView.dataSource = self
        activityTableView.register(UITableViewCell.self, forCellReuseIdentifier: "ActivityCell")
        
        self.addSubview(activityTableView)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = activityTableView.dequeueReusableCell(withIdentifier: "ActivityCell")
        
        return cell!
    }
}

