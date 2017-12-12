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
        
        buildingblocks.append(Buildingblock(id: "Wonen", name: "Wonen", description: "lekker wonen", blockType: BlockType.LIVING))
        buildingblocks.append(Buildingblock(id: "Werk", name: "Werk", description: "werken", blockType: BlockType.LIVING))
        buildingblocks.append(Buildingblock(id: "Verzekering", name: "Verzekering", description: "verzekering", blockType: BlockType.LIVING))
        buildingblocks.append(Buildingblock(id: "Gezondheid", name: "Gezondheid", description: "gezondheid", blockType: BlockType.LIVING))
        buildingblocks.append(Buildingblock(id: "Financien", name: "Financien", description: "financien", blockType: BlockType.LIVING))
        buildingblocks.append(Buildingblock(id: "Sociaal", name: "Sociaal", description: "sociaal", blockType: BlockType.LIVING))
                        
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
