//
//  ComponentService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 22/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class ComponentService : Service {
    func enrollClientInComponent(clientId: String, componentId: String) {
        apiClient.send(toRelativePath: "client/\(clientId)/component/\(componentId)",
            withHttpMethod: .post,
            onSuccessParser: { (results) in
            print(results)
        }) {
            print("failure")
        }
    }
}
