//
//  ComponentService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 22/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class ComponentService : Service {
    func getMockHomeComponents() -> [Component] {
        var homeComponents: [Component] = []
        homeComponents.append(Component(id: "1", name: "Woning zoeken", activities: activityService.getMockSearchHomeActivities()))
        homeComponents.append(Component(id: "2", name: "Zoek witgoed uit", activities: activityService.getMockBuyStuffActivities()))
        homeComponents.append(Component(id: "2", name: "Zoek witgoed uit", activities: activityService.getMockBuyStuffActivities()))

        return homeComponents
    }
    
    func getMockWorkComponents() -> [Component] {
        var workComponents: [Component] = []
        workComponents.append(Component(id: "3", name: "Zoek drie baantjes", activities: []))
        workComponents.append(Component(id: "4", name: "Maak een CV", activities: []))
        workComponents.append(Component(id: "4", name: "Maak een CV", activities: []))
        workComponents.append(Component(id: "4", name: "Maak een CV", activities: []))

        return workComponents
    }
    
    func getComponents(forClient clientId: Int, ofBuildingblock blockId: Int) -> () {
        apiClient.send(toRelativePath: "/clients/\(clientId)/blocks/\(blockId)/components",
            withHttpMethod: .get,
            onSuccessParser: { (_ data) in
                var components: [Component] = []
                do {
                    _ = try JSONDecoder().decode(
                        Component.self, from: data)
                }catch {
                    print("Json decoding failed")
                }
        }, onFailure: {
            print("something failed")
        })
    }
}
