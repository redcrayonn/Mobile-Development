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
    
    @IBOutlet var activityIndicatorBG: UIView!
    
    let imReadyAccount = "ImReadyAccount"
    let textFieldMoveDistance: Int = -250
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.authenticateUser()
        
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
        
        if Locksmith.loadDataForUserAccount(userAccount: imReadyAccount) != nil {
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
                            //                            if error.localizedDescription != "Canceled by user." {
                            self.showAlertWithTitle(title: "Er is iets fout gegaan.", message: self.checkTouchIDError(error: error))
                            //                            }
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
    
    @IBAction func onLoginClick(_ sender: Any) {
        startActivityIndicator(atVC: self, withView: view, andIndicatorBG: activityIndicatorBG)
        
//        let username = usernameField.text!
//        let password = passwordField.text!
        
        let username = "woutervermeij@gmail.com"
        let password = "wouter"
        
        if username != "" && password != "" {
            authenticationService.login(
                withUsername: username,
                andPassword: password,
                onSuccess: {
                    // Check if there is no Keychain data saved for this app
                    if Locksmith.loadDataForUserAccount(userAccount: self.imReadyAccount) == nil {
                        // If no keychain data; try to save username and access-token in keychain
                        do {
                            try Locksmith.saveData(
                                data: ["username": username,
                                       "access-token": CurrentUser.instance.access_token],
                                forUserAccount: self.imReadyAccount)
                        } catch {
                            print("could not store credentials in the keychain")
                            self.showAlertWithTitle(
                                title: "Er is iets fout gegaan.",
                                message: "Kon inloggegevens niet opslaan in de Keychain.")
                        }
                    }
                    
                    // Check which role the logged in user has and redirect to the correct storyboard
                    let user_role: Role = CurrentUser.instance.user_type!
                    
                    switch user_role {
                    case .CLIENT:
                        self.goToTabBarView(
                            inStoryboard: "Client",
                            withIdentifier: "TabBarController")
                        stopActivityIndicator(withIndicatorBG: self.activityIndicatorBG)
                    case .CAREGIVER:
                        self.goToTabBarView(
                            inStoryboard: "Caregiver",
                            withIdentifier: "TabBarController")
                        stopActivityIndicator(withIndicatorBG: self.activityIndicatorBG)
                    case .RELATIVE:
                        self.showAlertWithTitle(
                            title: "Not implemented yet",
                            message: "Relative is not yet implemented")
                        stopActivityIndicator(withIndicatorBG: self.activityIndicatorBG)
                    case .ADMIN:
                        self.showAlertWithTitle(
                            title: "Not implemented yet",
                            message: "Admin is not yet implemented")
                        stopActivityIndicator(withIndicatorBG: self.activityIndicatorBG)
                    default:
                        self.showAlertWithTitle(
                            title: "Er is iets fout gegaan met inloggen",
                            message: "Controleer uw gebruikersnaam en wachtwoord")
                        stopActivityIndicator(withIndicatorBG: self.activityIndicatorBG)
                    }
            }) {
                print("failed to log in")
                stopActivityIndicator(withIndicatorBG: self.activityIndicatorBG)
                
                self.showAlertWithTitle(title: "Ongeldige inlogggegevens", message: "Gebruikersnaam of wachtwoord is fout.")
            }
        } else {
            stopActivityIndicator(withIndicatorBG: self.activityIndicatorBG)
            
            self.showAlertWithTitle(title: "Velden niet ingevuld",
                                    message: "Vul alle velden in om in te loggen")
        }
    }
    
    //Show or hide the passwordinput
    @IBAction func onPasswordVisibilityClick(_ sender: Any) {
        if passwordField.text != "" {
            passwordField.isSecureTextEntry = !passwordField.isSecureTextEntry
        }
    }
    
    @IBAction func onRegisterFamilyClick(_ sender: Any) {
        // Change for production
        //        self.goToTabBarView(inStoryboard: "Caregiver", withIdentifier: "CaregiverTabBarController")
    }
    
    //    // When keyboard shows, move textfields up
    //        func textFieldDidBeginEditing(_ textField: UITextField) {
    //            moveTextField(textField: textField,
    //                          moveDistance: self.textFieldMoveDistance,
    //                          up: true)
    //        }
    //
    //    //When keyboard hides, move textfields back down
    //        func textFieldDidEndEditing(_ textField: UITextField) {
    //            moveTextField(textField: textField,
    //                          moveDistance: self.textFieldMoveDistance,
    //                          up: false)
    //        }
    //
    //        func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    //            textField.resignFirstResponder()
    //            return true
    //        }
    //
    //    //Function on how the textfields should move up
    //    func moveTextField(textField: UITextField, moveDistance: Int, up: Bool) {
    //        let moveDuration = 0.3
    //        let movement: CGFloat = CGFloat(up ? moveDistance : -moveDistance)
    //
    //        UIView.beginAnimations("animateTextField", context: nil)
    //        UIView.setAnimationBeginsFromCurrentState(true)
    //        UIView.setAnimationDuration(moveDuration)
    //        self.view.frame = self.view.frame.offsetBy(dx: 0, dy: movement)
    //        UIView.commitAnimations()
    //    }
    
    func checkTouchIDError(error: Error) -> String {
        var message: String!
        
        switch(error) {
        case LAError.authenticationFailed:
            message = "There was a problem verifying your identity."
        case LAError.userCancel:
            message = "Authentication was canceled by user."
        case LAError.userFallback:
            message = "The user tapped the fallback button"
        case LAError.systemCancel:
            message = "Authentication was canceled by system."
        case LAError.passcodeNotSet:
            message = "Passcode is not set on the device."
        case LAError.biometryNotAvailable:
            message = "TouchID is not available on the device."
        case LAError.biometryNotEnrolled:
            message = "TouchID has no enrolled fingers."
        case LAError.biometryLockout:
            message = "There were too many failed TouchID attempts and TouchID is now locked."
        case LAError.appCancel:
            message = "Authentication was canceled by application."
        case LAError.invalidContext:
            message = "LAContext passed to this call has been previously invalidated."
        default:
            message = "TouchID may not be configured"
            break
        }
        
        return message
    }
    
}
