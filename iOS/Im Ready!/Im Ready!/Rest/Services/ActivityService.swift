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
        
        activities.append(Activity(id: "1", name: "Zoek woningen", description: "Zoek meer woningen", points: 3, assignment: "Zoek meer woningen"))
        activities.append(Activity(id: "3", name: "Zoek drie banen", description: "Bekijk drie mogelijke banen", points: 2, assignment: "Zoek werk"))
        activities.append(Activity(id: "2", name: "Zoek verzekeringen", description: "Zoek meer verzekeringen", points: 4, assignment: "zoek meer verzekeringen"))
        
        return activities
    }
    
    func getMockSearchHomeActivities() -> [Activity] {
        var activities: [Activity] = []
        activities.append(Activity(id: "1", name: "Bekijk 3 makelaars", description: "bekijk makelaars", points: 2, assignment: "bekijk makelaars"))
        activities.append(Activity(id: "2", name: "Bekijk 5 huizen", description: "bekijk huizen", points: 2, assignment: "bekijk huizen"))
        
        return activities
    }
    
    func getMockBuyStuffActivities() -> [Activity] {
        var activities: [Activity] = []
        activities.append(Activity(id: "1", name: "Zoek een wasmachine", description: "zoek een wasmachine", points: 3, assignment: "Zoek een wasmachine"))
        activities.append(Activity(id: "2", name: "Zoek een droger", description: "zoek een droger", points: 2, assignment: "Zoek een droger"))
        
        return activities
    }
    
    public func getActivities(forClient clientId: Int,
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
