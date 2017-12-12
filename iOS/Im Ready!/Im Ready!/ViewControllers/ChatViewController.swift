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
        
        cell.recipientName.text = chats[indexPath.row].receiverId
        
        return cell
    }
    
    // Prepare navigation segue to ComponentViewController
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {        
//        if let destinationViewController = segue.destination as? ComponentViewController {
//            if let cell = sender as? BuildingblockCell {
//                
//            }
//        }
    }
}
