//
//  ComponentTableViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 27/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ComponentViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    var componentTableView: UITableView!
    var buildingblock: Buildingblock?
    var components: [Component]?
    var buildingblockImage: UIImageView?
    var activityView: ActivityView?
    var t_count: Int = 0
    var lastCell: ComponentStackViewCell = ComponentStackViewCell()
    var button_tag: Int = -1
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = buildingblock?.name
        components = buildingblock?.components
        
        componentTableView = UITableView(frame: view.frame)        
        componentTableView.separatorStyle = .none
        componentTableView.allowsSelection = false
        componentTableView.layer.frame.size.height = view.frame.height * 1.5
        componentTableView.frame.origin.y += 125
        componentTableView.register(UINib(nibName: "ComponentStackViewCell", bundle: nil),
                                    forCellReuseIdentifier: "ComponentStackViewCell")
        componentTableView.delegate = self
        componentTableView.dataSource = self
        view.addSubview(componentTableView)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == button_tag {
            return 320
        } else {
            return 80
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return (components?.count)!
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ComponentStackViewCell", for: indexPath) as! ComponentStackViewCell
        activityView?.activities = self.components![indexPath.row].activities!
        
        // If the cell does not exist yet, create a new one
        if !cell.cellExists {
            cell.openDetailViewBtn.setTitle(components![indexPath.row].name, for: .normal)
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
        self.componentTableView.beginUpdates()
        let previousCellTag = button_tag
        if lastCell.cellExists {
            self.lastCell.animate(duration: 0.2, whenDone: {
                self.view.layoutIfNeeded()
            })
            
            if sender.tag == button_tag {
                button_tag = -1
                lastCell = ComponentStackViewCell()
            }
        }
        if sender.tag != previousCellTag {
            button_tag = sender.tag
            
            lastCell = componentTableView.cellForRow(at: IndexPath(row: button_tag, section: 0)) as! ComponentStackViewCell
            self.lastCell.animate(duration: 0.2, whenDone: {
                self.view.layoutIfNeeded()
            })
        }
        
        self.componentTableView.endUpdates()
    }
}
