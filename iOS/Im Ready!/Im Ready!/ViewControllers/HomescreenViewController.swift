//
//  ViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit

class HomescreenViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func logoutButton(_ sender: Any) {
        let storyBoard : UIStoryboard = UIStoryboard(name: "Client", bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(
            withIdentifier: "SBLogin") as! UIViewController
        self.present(nextViewController, animated:true, completion:nil)
    }
    

}

