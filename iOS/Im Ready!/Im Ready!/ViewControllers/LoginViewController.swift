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
import CryptoSwift

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
        
        // Check if there is keychain data on the device
        if Locksmith.loadDataForUserAccount(userAccount: imReadyAccount) != nil {
            
            // Check the fingerprint
            authenticationContext.evaluatePolicy(
                .deviceOwnerAuthenticationWithBiometrics,
                localizedReason: "Log in met je vingerafdruk") {
                    (wasSuccessful, error) in
                    
                    // If valid fingerprint, you may enter the app
                    if (wasSuccessful) {
                        
                        // retrieve keychain data to check what kind of user we have, to redirect to the correct flow
                        let keychainDict = Locksmith.loadDataForUserAccount(userAccount: self.imReadyAccount)
                        CurrentUser.instance.user_type = Role(rawValue: keychainDict?["user-role"] as! String)
                        CurrentUser.instance.id = (keychainDict?["id"] as! String)
                        
                        self.redirectUserToStoryboard()
                    }
                        // Invalid fingerprint or cancelled
                    else {
                        // Check if there is an error
                        if let error = error {
                            // Only show an error when something other happens then User cancelation
                            if error.localizedDescription != "Canceled by user." {
                                simpleAlert(atVC: self,
                                            withTitle: "Er is iets fout gegaan",
                                            andMessage: self.checkTouchIDError(error: error))
                            }
                        }
                    }
            }
        }
    }
    
    @IBAction func onLoginClick(_ sender: Any) {
        startActivityIndicator(atVC: self, withView: view, andIndicatorBGView: activityIndicatorBG)
        
//                let username = usernameField.text!
//                let password = passwordField.text!.sha256()
        
//
//        let username = "woutervermeij@gmail.com"
//        let password = "wouter"
        
        let username = "woutertest4@gmail.com"
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
                                       "id": CurrentUser.instance.id as Any,
                                       "access-token": CurrentUser.instance.access_token as Any,
                                       "user-role": CurrentUser.instance.user_type?.rawValue as Any],
                                forUserAccount: self.imReadyAccount)
                        } catch {
                            print("could not store credentials in the keychain")
                            simpleAlert(atVC: self,
                                        withTitle: "Er is iets fout gegaan",
                                        andMessage: "Kon inloggegevens niet opslaan in de keychain")
                        }
                    }
                    
                    self.redirectUserToStoryboard()
                    
            }) {
                print("failed to log in")
                stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
                simpleAlert(atVC: self,
                            withTitle: "Ongeldige inloggegevens",
                            andMessage: "Gebruikersnaam of wachtwoord is fout")
            }
        } else {
            stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
            simpleAlert(atVC: self,
                        withTitle: "Velden niet ingevuld",
                        andMessage: "Vul alle velden in om in te loggen")
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
    //        func textFieldDidBeginEditing(_ textField: UITextField) {s
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
    
    func redirectUserToStoryboard() {
        // Check which role the logged in user has and redirect to the correct storyboard
        let user_role: Role = CurrentUser.instance.user_type!
        
        switch user_role {
        case .CLIENT:
            self.goToTabBarView(
                inStoryboard: "Client",
                withIdentifier: "TabBarController")
            stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
        case .CAREGIVER:
            self.goToTabBarView(
                inStoryboard: "Caregiver",
                withIdentifier: "TabBarController")
            stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
        case .RELATIVE:
            simpleAlert(atVC: self, withTitle: "Not implemented yet",
                        andMessage: "Relative is not yet implemented")
            stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
        case .ADMIN:
            simpleAlert(atVC: self, withTitle: "Not implemented yet",
                        andMessage: "Admin is not yet implemented")
            stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
        default:
            simpleAlert(atVC: self,
                        withTitle: "Er is iets fout gegaan met inloggen",
                        andMessage: "Controller gebruikersnaam en wachtwoord")
            stopActivityIndicator(withIndicatorBGView: self.activityIndicatorBG)
        }
    }
    
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
