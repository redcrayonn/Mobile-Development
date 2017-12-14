//
//  LoginViewController.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 13/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import UIKit
import LocalAuthentication
import Locksmith

class LoginViewController: UIViewController, UITextFieldDelegate {
    @IBOutlet var usernameField: UITextField!
    @IBOutlet var passwordField: UITextField!
    @IBOutlet var loginButton: UIButton!    
    @IBOutlet var registerFamilyButton: UIButton!
    
    let textFieldMoveDistance: Int = -250
    
    override func viewDidLoad() {
        super.viewDidLoad()
        authenticateUser()
        self.hideKeyboardWhenTappedAround()
        //        usernameField.resignFirstResponder()
        //        passwordField.resignFirstResponder()
    }
    
    func authenticateUser() {
        // Create authenticationContext to use TouchID
        let authenticationContext = LAContext()
        var error:NSError?
        
        // Check if the device has a fingerprint sensor
        guard authenticationContext.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error) else {
            // If the device does not have TouchID, return nothing and let them login regularly
            return
            
        }
        // Check the fingerprint
        authenticationContext.evaluatePolicy(
            .deviceOwnerAuthenticationWithBiometrics,
            localizedReason: "Log in met je vingerafdruk") {
                (wasSuccessful, error) in
                // If valid fingerprint, you may enter the app
                if (wasSuccessful) {
                    self.goToTabBarView(inStoryboard: "Client", withIdentifier: "ClientTabBarController")
                }
                    // Invalid fingerprint or cancelled
                else {
                    // Check if there is an error
                    if let error = error {
                        // Only show an error when something other happens then User cancelation
                        if error.localizedDescription != "Canceled by user." {
                            self.showAlertWithTitle(title: "TouchID cancelled", message: error.localizedDescription)
                        }
                    }
                }
        }
    }
    
    func showAlertWithTitle(title: String, message: String ) {
        let alertVC = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okAction = UIAlertAction(title: "Ok", style: .default, handler: nil)
        alertVC.addAction(okAction)
        DispatchQueue.main.async() { () -> Void in
            self.present(alertVC, animated: true, completion: nil)
        }
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func onLoginClick(_ sender: Any) {
        let username = usernameField.text!
        let password = passwordField.text!
        //        if let username = usernameField.text, let password = passwordField.text {
        authenticationService.login(withUsername: username,
                                    andPassword: password,
                                    onSuccess: {
                                        
                                        // Try to save credentials in keychain
                                        do {
                                            try Locksmith.saveData(data: ["password":password], forUserAccount: username)
                                        } catch {
                                            print("could not store credentials in the keychain")
                                        }
                                        
                                        self.goToTabBarView(
                                            inStoryboard: "Client",
                                            withIdentifier: "ClientTabBarController")
        }) {
            print("failed to log in")
            
            // Remove for production
            self.goToTabBarView(inStoryboard: "Client", withIdentifier: "ClientTabBarController")
        }
        //        }
        
    }
    
    //Show or hide the passwordinput
    @IBAction func onPasswordVisibilityClick(_ sender: Any) {
        if passwordField.text != "" {
            passwordField.isSecureTextEntry = !passwordField.isSecureTextEntry
        }
    }
    
    @IBAction func onRegisterFamilyClick(_ sender: Any) {
        // Change for production
        self.goToTabBarView(inStoryboard: "Caregiver", withIdentifier: "CaregiverTabBarController")
    }
    
    // When keyboard shows, move textfields up
    //    func textFieldDidBeginEditing(_ textField: UITextField) {
    //        moveTextField(textField: textField,
    //                      moveDistance: self.textFieldMoveDistance,
    //                      up: true)
    //    }
    
    //When keyboard hides, move textfields back down
    //    func textFieldDidEndEditing(_ textField: UITextField) {
    //        moveTextField(textField: textField,
    //                      moveDistance: self.textFieldMoveDistance,
    //                      up: false)
    //    }
    //
    //    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    //        textField.resignFirstResponder()
    //        return true
    //    }
    
    //Function on how the textfields should move up
    func moveTextField(textField: UITextField, moveDistance: Int, up: Bool) {
        let moveDuration = 0.3
        let movement: CGFloat = CGFloat(up ? moveDistance : -moveDistance)
        
        UIView.beginAnimations("animateTextField", context: nil)
        UIView.setAnimationBeginsFromCurrentState(true)
        UIView.setAnimationDuration(moveDuration)
        self.view.frame = self.view.frame.offsetBy(dx: 0, dy: movement)
        UIView.commitAnimations()
    }
}
