//
//  ApiClient.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//
// https://guides.cocoapods.org/using/getting-started.html

import Foundation
import Alamofire
import SwiftyJSON

public class ApiClient {
    private let baseUrl: String = "https://imreadyapiv2.azurewebsites.net/api/"
    
    public func send(toRelativePath url: String,
                     withHttpMethod httpMethod: HTTPMethod,
                     withParameters parameters: [String: Any] = [:],
                     withHeaders headers: [String : String] = [:],
                     withEncoding encoding: ParameterEncoding = URLEncoding.default,
                     onSuccessParser onSuccess: @escaping (_ data: Data) -> (),
                     onFailure: @escaping () -> ()) -> () {
                
//        print(baseUrl + url)
        
        Alamofire.request(baseUrl + url,
                          method: httpMethod,
                          parameters: parameters,
                          encoding: encoding,
                          headers: headers).responseData { response in
                            if response.error != nil {
                                print("Error: \(String(describing: response.error))")
                                onFailure()
                            }
                            
                            if let data = response.data, let utf8Text = String(data: data, encoding: .utf8) {
                                // Uncomment the next two lines if you want to see the response data
//                                print("Response data: \(response.data)")
//                                print("Data: \(utf8Text)")
                                onSuccess(data)
                            }
        }
    }
}
