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
    var activities: [ClientActivity] = []
    var component: ClientComponent!
    
    var t_count:Int = 0
    var lastCell: ClientActivityStackViewCell = ClientActivityStackViewCell()
    var button_tag:Int = -1
    var height: Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = component.name
        self.hideKeyboardWhenTappedAround()
        
        tableView = UITableView(frame: view.frame)
        tableView.separatorColor = UIColor.white
        tableView.allowsSelection = false
        tableView.layer.frame.size.height = view.frame.height * 1.5
//        tableView.frame.origin.y += 100
        tableView.register(UINib(nibName: "ActivityStackViewCell", bundle: nil),
                           forCellReuseIdentifier: "ActivityStackViewCell")
        tableView.delegate = self
        tableView.dataSource = self
        
        view.addSubview(tableView)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == button_tag {
            return 450
        } else {
            return 60
        }
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return activities.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ActivityStackViewCell", for: indexPath) as! ClientActivityStackViewCell
        
        let activity = activities[indexPath.row]
        
        cell.remarksTextView.isHidden = true
        cell.remarksLbl.isHidden = true
        
        // If the cell does not exist yet, create a new one
        if !cell.cellExists {
            cell.activityDescriptionLbl.adjustsFontSizeToFitWidth = true
            cell.activityDescriptionLbl.numberOfLines = 0
            cell.activityDescriptionLbl.text = activity.description
            cell.activityDescriptionLbl.sizeToFit()
            
            cell.openDetailViewBtn.setTitle(activity.name, for: .normal)
            cell.openDetailViewBtn.tag = t_count
            cell.openDetailViewBtn.addTarget(self, action: #selector(cellOpened(sender:)), for: .touchUpInside)
            
            cell.answerTextView.layer.borderColor = UIColor.gray.cgColor
            cell.answerTextView.layer.borderWidth = 1.0
            cell.answerTextView.layer.shadowColor = UIColor.black.cgColor
            cell.answerTextView.layer.shadowOffset = CGSize(width: 1.0, height: 1.0)
            
            cell.activity = activity
            cell.view = self
            cell.deadlineLbl.text = "Deadline in 2 dagen"
            
            // Set bottom "border" for unfolded view
            cell.detailView.layer.backgroundColor = UIColor.white.cgColor
            cell.detailView.layer.masksToBounds = false
            cell.detailView.layer.shadowColor = UIColor.gray.cgColor
            cell.detailView.layer.shadowOffset = CGSize(width: 0.0, height: 1.0)
            cell.detailView.layer.shadowOpacity = 1.0
            cell.detailView.layer.shadowRadius = 0.0
            
            if let content = activity.content {
                cell.answerTextView.text = content
                cell.answerTextView.isEditable = false
                cell.sendAnswerBtn.isHidden = true
                cell.deadlineLbl.isHidden = true
            }
            
            if activity.feedback != nil {
                cell.remarksTextView.isHidden = false
                cell.remarksTextView.layer.borderColor = UIColor.orange.cgColor
                cell.remarksTextView.layer.borderWidth = 1.0
                cell.remarksTextView.layer.backgroundColor = UIColor.orange.cgColor
                cell.remarksTextView.alpha = 0.5
                cell.remarksLbl.isHidden = false
                
                cell.remarksTextView.text = activity.feedback?.content
            }
            
            cell.cellExists = true
            t_count += 1
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
