//
//  ActivityViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 07/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ActivityViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    var tableView: UITableView!
    var activities: [Activity] = []
    var t_count:Int = 0
    var lastCell: ActivityStackViewCell = ActivityStackViewCell()
    var button_tag:Int = -1
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView = UITableView(frame: view.frame)
        tableView.separatorStyle = .none
        tableView.allowsSelection = false
        tableView.layer.frame.size.height = view.frame.height * 1.5
        tableView.frame.origin.y += 125
        tableView.register(UINib(nibName: "ActivityStackViewCell", bundle: nil),
                           forCellReuseIdentifier: "ActivityStackViewCell")
        tableView.delegate = self
        tableView.dataSource = self
        view.addSubview(tableView)
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ActivityStackViewCell", for: indexPath) as! ActivityStackViewCell
        
        // If the cell does not exist yet, create a new one
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
    
    // If a ActivityStackViewCell is openend, do the animation
    @objc func cellOpened(sender: UIButton) {
        self.tableView.beginUpdates()
        let previousCellTag = button_tag
        if lastCell.cellExists {
            self.lastCell.animate(duration: 0.2, whenDone: {
                self.view.layoutIfNeeded()
            })
            
            if sender.tag == button_tag {
                button_tag = -1
                lastCell = ActivityStackViewCell()
            }
        }
        if sender.tag != previousCellTag {
            button_tag = sender.tag
            
            lastCell = tableView.cellForRow(at: IndexPath(row: button_tag, section: 0)) as! ActivityStackViewCell
            self.lastCell.animate(duration: 0.2, whenDone: {
                self.view.layoutIfNeeded()
            })
        }
        
        self.tableView.endUpdates()
    }
}
