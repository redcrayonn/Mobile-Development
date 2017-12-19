//
//  ActivityService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 23/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class ActivityService : Service {
    func getMockActivities() -> [Activity] {
        var activities: [Activity] = []
        
        
        return activities
    }
    
    func getMockSearchHomeActivities() -> [Activity] {
        var activities: [Activity] = []
        
        return activities
    }
    
    func getMockBuyStuffActivities() -> [Activity] {
        var activities: [Activity] = []
        
        return activities
    }
    
    func getActivities(forClient clientId: Int,
                              withBlock blockId: Int,
                              andComponent componentId: Int,
                              onSuccess: @escaping ([Activity]) -> (),
                              onFailure: @escaping () -> ()) -> (){
        apiClient.send(toRelativePath: "/clients/\(clientId)/blocks/\(blockId)/components/\(componentId)/activities",
            withHttpMethod: .get, onSuccessParser: { (response) in
                var activities: [Activity] = []
                print(response)
                onSuccess(activities)
        }) {
            print("Failed something")
            onFailure()
        }
    }
}
