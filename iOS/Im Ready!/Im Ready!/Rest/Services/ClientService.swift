//
//  ClientService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 16/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class ClientService : Service{
    
    func getFutureplan(ofClient id: String,
                       onSuccess: @escaping (_ : FutureplanResult) -> (),
                       onFailure: @escaping () -> ()) {
        apiClient.send(toRelativePath: "client/\(id)/futureplan",
            withHttpMethod: .get,
            onSuccessParser: { (_ data) in
                do {
                    let results = try JSONDecoder().decode(
                        FutureplanResult.self, from: data)
                    print(results)
                    
                    onSuccess(results)
                } catch {
                    onFailure()
                }
                
        }) {
            onFailure()
            print("something failed")
        }
    }
}
