//
//  ActivityViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 07/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ClientActivityViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    var tableView: UITableView!
    var activities: [ClientActivities] = []
    var component: ClientComponent!
        
    var t_count:Int = 0
    var lastCell: ClientActivityStackViewCell = ClientActivityStackViewCell()
    var button_tag:Int = -1
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = component.name
        
        tableView = UITableView(frame: view.frame)
        tableView.separatorColor = UIColor.white
        tableView.allowsSelection = false
        tableView.layer.frame.size.height = view.frame.height * 1.5
        tableView.frame.origin.y += 100
        tableView.register(UINib(nibName: "ActivityStackViewCell", bundle: nil),
                           forCellReuseIdentifier: "ActivityStackViewCell")
        tableView.delegate = self
        tableView.dataSource = self
        
        view.addSubview(tableView)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == button_tag {
            return 320
        } else {
            return 60
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return activities.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ActivityStackViewCell", for: indexPath) as! ClientActivityStackViewCell
        
        cell.activityDescription.text = activities[indexPath.row].description
        
        // If the cell does not exist yet, create a new one
        if !cell.cellExists {
            cell.openDetailViewBtn.setTitle(activities[indexPath.row].name, for: .normal)
            cell.openDetailViewBtn.tag = t_count
            cell.openDetailViewBtn.addTarget(self, action: #selector(cellOpened(sender:)), for: .touchUpInside)
            t_count += 1
            cell.cellExists = true
            
            cell.answerTextView.layer.borderColor = UIColor.black.cgColor
            cell.answerTextView.layer.borderWidth = 1.0
            
            cell.answerTextView.layer.shadowColor = UIColor.black.cgColor
            cell.answerTextView.layer.shadowOpacity = 1.0
            cell.answerTextView.layer.shadowOffset = CGSize.zero
            cell.answerTextView.layer.shadowRadius = 10
            cell.answerTextView.layer.shouldRasterize = true

            
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
                lastCell = ClientActivityStackViewCell()
            }
        }
        if sender.tag != previousCellTag {
            button_tag = sender.tag
            
            lastCell = tableView.cellForRow(at: IndexPath(row: button_tag, section: 0)) as! ClientActivityStackViewCell
            self.lastCell.animate(duration: 0.2, whenDone: {
                self.view.layoutIfNeeded()
            })
        }
        
        self.tableView.endUpdates()
    }
}
