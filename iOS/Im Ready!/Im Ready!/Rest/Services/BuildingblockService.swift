//
//  BuildingblockService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 20/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class BuildingblockService : Service {
    func getMockBuildingblocks() -> [Buildingblock] {
        var buildingblocks: [Buildingblock] = []
        
        buildingblocks.append(Buildingblock(id: "1", name: "Wonen", description: "lekker wonen", imageURL: "wonen.png", components: componentService.getMockHomeComponents()))
        buildingblocks.append(Buildingblock(id: "2", name: "Werk", description: "werken", imageURL: "wonen.png", components: componentService.getMockWorkComponents()))
        buildingblocks.append(Buildingblock(id: "3", name: "Financien", description: "financiele dingen", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "4", name: "Verzekering", description: "verzekeren", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "2", name: "Werk", description: "werken", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "2", name: "Gezondheid", description: "werken", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "2", name: "Werk", description: "werken", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "2", name: "Werk", description: "werken", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "2", name: "Werk", description: "werken", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "2", name: "Rechten en plichten", description: "werken", imageURL: "wonen.png", components: []))
        buildingblocks.append(Buildingblock(id: "2", name: "Behandelplan", description: "werken", imageURL: "wonen.png", components: []))
        
        return buildingblocks
    }
    
    func getBuildingblocks(forClient clientId: Int,
                           onSuccess: @escaping ([Buildingblock]) -> (),
                           onFailure: @escaping () -> ()) -> () {
        
        apiClient.send(toRelativePath: "/clients/\(clientId)/blocks/",
            withHttpMethod: .get,
            onSuccessParser: { (response) in
                var buildingblocks: [Buildingblock] = []
                print(response)
                onSuccess(buildingblocks)
        }) {
            print("failed")
            onFailure()
        }
    }
}
