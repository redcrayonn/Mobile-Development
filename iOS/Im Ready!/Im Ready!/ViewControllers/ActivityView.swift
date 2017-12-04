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
    
    var t_count: Int = 0
    var lastCell: ActivityStackViewCell = ActivityStackViewCell()
    var button_tag: Int = -1
    
    override init(frame: CGRect){
        super.init(frame: frame)
        
        // Initialize the tableview
        activityTableView = UITableView(frame: CGRect(x: 0, y: 0, width: screenWidth*0.5, height: screenHeight))
        activityTableView.separatorStyle = .none
        activityTableView.allowsSelection = false
        activityTableView.layer.frame.size.height = self.frame.height * 1.5
        activityTableView.frame.origin.y += 125
        activityTableView.register(UINib(nibName: "ActivityCell", bundle: nil),
                                   forCellReuseIdentifier: "ActivityCell")
        activityTableView.delegate = self
        activityTableView.dataSource = self
        activityTableView.register(UITableViewCell.self, forCellReuseIdentifier: "ActivityCell")
        
        self.addSubview(activityTableView)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return activities.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = activityTableView.dequeueReusableCell(withIdentifier: "ActivityCell") as! ActivityStackViewCell
        
        if !cell.cellExists {
            cell.openDetailViewBtn.setTitle(activities[indexPath.row].name, for: .normal)
            cell.openDetailViewBtn.tag = t_count
            cell.openDetailViewBtn.addTarget(self, action: #selector(cellOpened(sender:)), for: .touchUpInside)
            t_count += 1
            cell.cellExists = true
        }
        
        UIView.animate(withDuration:  0) {
            cell.contentView.layoutIfNeeded()
        }
        
        return cell
    }
    
    @objc func cellOpened(sender: UIButton) {
        self.activityTableView.beginUpdates()
        let previousCellTag = button_tag
        if lastCell.cellExists {
            self.lastCell.animate(duration: 0.2, whenDone: {
                self.layoutIfNeeded()
            })
            
            if sender.tag == button_tag {
                button_tag = -1
                lastCell = ActivityStackViewCell()
            }
        }
        if sender.tag != previousCellTag {
            button_tag = sender.tag
            
            lastCell = activityTableView.cellForRow(at: IndexPath(row: button_tag, section: 0)) as! ActivityStackViewCell
            self.lastCell.animate(duration: 0.2, whenDone: {
                self.layoutIfNeeded()
            })
        }
        
        self.activityTableView.endUpdates()
    }
}

