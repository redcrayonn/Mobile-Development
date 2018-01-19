//
//  ActivityViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 07/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import Timepiece
import DZNEmptyDataSet

class ClientActivityViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, DZNEmptyDataSetSource, DZNEmptyDataSetDelegate {
    
    var tableView: UITableView!
    
    var activities: [ClientActivity] = []
    var component: ClientComponent!
    let convertDatetime: ConvertDatetime = ConvertDatetime()
    var lastCell: ClientActivityStackViewCell = ClientActivityStackViewCell()
    
    var t_count:Int = 0
    var button_tag:Int = -1
    var height: Int = 0
    var cellDetailViewHeight: CGFloat = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = component.name
        self.hideKeyboardWhenTappedAround()
        
        tableView = UITableView(frame: view.frame)
        tableView.separatorColor = UIColor.white
        tableView.allowsSelection = false
        tableView.layer.frame.size.height = view.frame.height * 1.5
        tableView.register(UINib(nibName: "ActivityStackViewCell", bundle: nil),
                           forCellReuseIdentifier: "ActivityStackViewCell")
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.emptyDataSetSource = self;
        self.tableView.emptyDataSetDelegate = self;
        
        view.addSubview(tableView)
    }
    
    func title(forEmptyDataSet scrollView: UIScrollView!) -> NSAttributedString! {
        let string = "Geen activiteiten"
        let attribute = [ NSAttributedStringKey.foregroundColor: UIColor.gray ]
        let attributedString = NSAttributedString(string: string, attributes: attribute)
        
        return attributedString
    }
    
    func description(forEmptyDataSet scrollView: UIScrollView!) -> NSAttributedString! {
        let string = "Er zijn geen activiteiten om te maken!"
        let attribute = [ NSAttributedStringKey.foregroundColor: UIColor.gray ]
        let attributedString = NSAttributedString(string: string, attributes: attribute)
        
        return attributedString
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == button_tag {
            //            return detailViewHeight
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
        let status = String(describing: Status(rawValue: activity.status!)!)
        
        cell.remarksTextView.isHidden = true
        cell.remarksLbl.isHidden = true
        cell.statusLbl.isHidden = true
        
        cell.activityDescriptionLbl.text = activity.description
        cell.openDetailViewBtn.setTitle(activity.name, for: .normal)
        cell.openDetailViewBtn.tag = t_count
        cell.openDetailViewBtn.addTarget(self, action: #selector(cellOpened(sender:)), for: .touchUpInside)
        
        cell.activity = activity
        cell.view = self
        
        let deadline = convertDatetime.toTimeOrDateString(fromDateTime: activity.deadline!, convertTo: .date)
        cell.deadlineLbl.text = "Deadline\n \(deadline)"
        
        // Set bottom "border" for unfolded view
        cell.styleCell()
        
        if let content = activity.answer {
            cell.answerTextView.text = content
            cell.answerTextView.isEditable = false
            cell.sendAnswerBtn.isHidden = true
            cell.deadlineLbl.isHidden = true
            cell.statusLbl.text = status
        }
        
        if (activity.feedback?.count)! > 0 {
            cell.remarksTextView.isHidden = false
            cell.remarksTextView.alpha = 0.5
            cell.remarksLbl.isHidden = false
            
            var remarks: String = ""
            for f in activity.feedback! {
                remarks.append(f.content!)
            }
            cell.remarksTextView.text = remarks
        }
                
        cell.cellExists = true
        t_count += 1
        
        UIView.animate(withDuration:  0) {
            cell.contentView.layoutIfNeeded()
        }
        
        return cell
    }
    
    func calculateHeight(forCell cell: ClientActivityStackViewCell) -> CGFloat{
        let height: CGFloat = cell.activityDescriptionLbl.frame.height +
            cell.remarksLbl.frame.height +
            cell.answerTextView.frame.height +
            cell.statusLbl.frame.height + 450
        
        return height
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
