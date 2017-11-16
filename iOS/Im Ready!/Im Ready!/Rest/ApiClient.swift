//
//  ApiClient.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

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
    public func send(toRelativePath url: String,
                     withHttpMethod httpMethod: HttpMethod = HttpMethod.GET,
                     withParameters parameters: [String : Any] = [:],
                     encodedAs encoding: String.Encoding = .utf8,
                     withHeaders headers: [String : String] = [:],
                     onSuccesParser onSucces: @escaping (_ data: Data) -> (),
                     onFailure: @escaping () -> ()) -> URLSessionTask? {
        
        // relative urlpath for this apiclient
        let baseUrl = URL(string: "https://inhollandbackend.azurewebsites.net")
        
        // authtoken header as defined by api
        var requestHeaders = headers
        requestHeaders["x-authtoken"] = UserDefaults.standard.string(forKey: "session")
        
        // create url for passing to webclient
        if let url = URL(string: url, relativeTo: baseUrl) {
            // make web call
            return WebClient.send( withUrl: url,
                                   withHttpMethod: httpMethod,
                                   withParameters: parameters,
                                   encodedAs: encoding,
                                   withHeaders: requestHeaders,
                                   onSucces: onSucces,
                                   onFailure: onFailure)
        }
        
        // the url was malformed, so return nil
        return nil
    }
}
