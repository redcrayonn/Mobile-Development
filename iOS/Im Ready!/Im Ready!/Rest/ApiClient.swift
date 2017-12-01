//
//  ApiClient.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import Alamofire
import SwiftyJSON

public class ApiClient {
    /**
     Sends a web request to a passed url relative to it's api url
     
     - parameters:
     - toRelativePath: The path to send the request to relative to the api url
     - withHttpMethod: The httpmethod of the request (ex. GET, POST)
     - withParameters: A dictionary containing data to send as url encoded parameters.
     - withHeaders: A dictionary containing data to send as the body of the request.
     - onSuccesParser: Closure to call if the request was succesful (statuscode 200) which parses the incoming data to a useable object
     - onFailure: Closure to call if the request failed for any reason
     
     - returns: The URLSessionTask object that makes the request. By capturing this object, a request can be cancelled at any time by calling .cancel()
     */
    
    let baseUrl: String = "https://www.imready.ml/api/v1"
    
    public func send(toRelativePath url: String,
                     withHttpMethod httpMethod: HTTPMethod,
                     withParameters parameters: [String: Any] = [:],
                     withHeaders headers: [String : String] = [:],
                     onSuccessParser onSuccess: @escaping (_ data: Data) -> (),
                     onFailure: @escaping () -> ()) -> () {
        
        Alamofire.request(baseUrl + url,
                          method: httpMethod,
                          parameters: parameters,
                          encoding: JSONEncoding.default,
                          headers: headers).responseData { response in
                            if response.error != nil {
                                print("Error: \(String(describing: response.error))")
                                onFailure()
                            }
                            
                            if let data = response.data, let utf8Text = String(data: data, encoding: .utf8) {
                                print("Response data: \(response.data)")
                                print("Data: \(utf8Text)")
                                onSuccess(data)
                            }
        }
    }
}
