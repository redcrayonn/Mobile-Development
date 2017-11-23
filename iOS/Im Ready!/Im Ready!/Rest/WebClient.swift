////
////  WebClient.swift
////  Im Ready!
////
////  Created by Wouter Vermeij on 16/11/2017.
////  Copyright © 2017 Wouter Vermeij. All rights reserved.
////
//
//import Foundation
//
//public class WebClient {
//    
//    /**
//     Sends a web request to a passed url
//     
//     - parameters:
//     - withUrl: The url to send the request to
//     - withHttpMethod: The httpmethod of the request (ex. GET, POST)
//     - withParameters: A dictionary containing data to send as url encoded parameters.
//     - encodedAs: The encoding that the paramters are encoded as
//     - withHeaders: A dictionary containing data to send as the body of the request.
//     - onSucces: Closure to call if the request was succesful (statuscode 200)
//     - onFailure: Closure to call if the request failed for any reason
//     
//     - returns:
//     */
//    static func send(withUrl url: URL,
//                     withHttpMethod httpMethod: HttpMethod = HttpMethod.GET,
//                     withParameters parameters: [String : Any] = [:],
//                     encodedAs encoding: String.Encoding = .utf8,
//                     withHeaders headers: [String : String] = [:],
//                     onSucces: @escaping (_ data: Data) -> (),
//                     onFailure: @escaping () -> ()) -> URLSessionTask {
//        var request = URLRequest(url: url)
//        // Set httpmethod if specified, otherwise default to "GET" request
//        request.httpMethod = httpMethod.asString
//        
//        // paramaters
//        if let paramData = parameters.stringFromHttpParameters().data(using: encoding){
//            request.httpBody = paramData
//        }
//        
//        // headers
//        for (key, value) in headers {
//            request.addValue(value, forHTTPHeaderField: key)
//        }
//        
//        // get the application's shared session
//        let session = URLSession.shared
//        
//        // prepare async call and save for returning
//        let task = session.dataTask(with: request,
//                                    completionHandler: {(optData: Data?,
//                                        response: URLResponse?,
//                                        error: Error?) -> () in
//                                        
//                                        if let data = optData {
//                                            if let urlresponse = response as? HTTPURLResponse {
//                                                if urlresponse.statusCode == 200 {
//                                                    // succes closure
//                                                    onSucces(data)
//                                                } else {
//                                                    print("Request failed with statuscode: \(urlresponse.statusCode)")
//                                                    // failed closure
//                                                    onFailure()
//                                                }
//                                            }
//                                        } else {
//                                            // failed to send
//                                            print(error!)
//                                        }
//        })
//        // do async call
//        task.resume()
//        // return task for potential cancelling
//        return task
//    }
//}

