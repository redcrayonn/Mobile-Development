//
//  ComponentService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 22/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class ComponentService : Service {
    func getMockComponents() -> [Component]{
        var components: [Component] = []
        
        components.append(Component(id: "1", name: "Woning zoeken", activities: activityService.getMockActivities()))
        components.append(Component(id: "2", name: "Verzekering zoeken", activities: []))
        components.append(Component(id: "3", name: "Werk zoeken", activities: []))
        
        return components
    }
    
    func getComponents(forClient clientId: Int, ofBuildingblock blockId: Int) -> () {
        apiClient.send(toRelativePath: "clients/\(clientId)/blocks/\(blockId)/components",
                       withHttpMethod: .get,
                       onSuccessParser: { (_ data) in
                        var components: [Component] = []
                        do {
                            _ = try JSONDecoder().decode(
                                Component.self, from: data)
                        }catch {
                            
                        }
        }, onFailure: {
        })
    }
}
