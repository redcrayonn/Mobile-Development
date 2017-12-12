//
//  ChatViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 04/12/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class ChatViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDataSource {
    @IBOutlet weak var collectionView: UICollectionView!
    
    var chats: [Chat] = []
    let cellSpacingHeight: CGFloat = 5
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        chats = chatsService.getMockChats()
        
        collectionView.delegate = self
        collectionView.dataSource = self
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return chats.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ChatCell", for: indexPath) as! ChatCollectionViewCell
        
        let receiver: User = userService.getMockUser(withId: chats[indexPath.row].receiverId!)!
        cell.recipient = receiver
        cell.recipientName.text = receiver.firstname
        
        cell.cellView.layer.borderColor = UIColor.black.cgColor
        cell.cellView.layer.borderWidth = 1
        
//        cell.cellView.layer.shadowOffset = CGSize(width: 0, height: 0)
//        cell.cellView.layer.shadowColor = UIColor.black.cgColor
//        cell.cellView.layer.shadowRadius = 4
//        cell.cellView.layer.shadowOpacity = 0.25
//        cell.cellView.layer.masksToBounds = false;
//        cell.cellView.clipsToBounds = false;

        
        return cell
    }
    
    // Prepare navigation segue to MessageViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {        
        if let destinationViewController = segue.destination as? MessageViewController {
            if let cell = sender as? ChatCollectionViewCell {
                destinationViewController.recipient = cell.recipient
            }
        }
    }
}
