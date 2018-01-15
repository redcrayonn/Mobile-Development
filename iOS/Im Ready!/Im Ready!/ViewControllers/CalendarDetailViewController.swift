//
//  CalendarDetailViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 15/01/2018.
//  Copyright © 2018 Wouter Vermeij. All rights reserved.
//

import UIKit

class CalendarDetailViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tableView: UITableView!
    
    var details: [String] = ["Titel", "Opmerking", "Overig"]

    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Afspraak details"
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return details.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        
        return cell
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
