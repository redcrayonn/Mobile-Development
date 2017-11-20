//
//  BuildingblockService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 20/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class BuildingblockService : Service {
    func getBuildingblocks() -> [Buildingblock] {
//        var buildingblockResult: Buildingblock
        apiClient.send(toRelativePath: "blocks", onSuccesParser: { (_ data: Data) in
            print("test")
        }) {
            print("Failure")
        }
//        apiClient.send(toRelativePath: "blocks", onSuccesParser: { (_ data: Data) in
//            let result = try JSONDecoder().decode(Buildingblock.self, from: data)
//            buildingblockResult = result
//
//            // call caller succes closure
//            onSuccess()
//
//            }) {
//                print("something failed here waddup")
//                onFailure()
//        }
        
        let buildingblock1: Buildingblock = Buildingblock(id: "1", name: "wonen", description: "lekker wonen", imageURL: "wonen.png", components: [])
        let buildingblock2: Buildingblock = Buildingblock(id: "2", name: "werk", description: "werken", imageURL: "wonen.png", components: [])
        let buildingblock3: Buildingblock = Buildingblock(id: "3", name: "financien", description: "financiele dingen", imageURL: "wonen.png", components: [])
        let buildingblock4: Buildingblock = Buildingblock(id: "4", name: "verzekring", description: "verzekeren", imageURL: "wonen.png", components: [])
        let buildingblock5: Buildingblock = Buildingblock(id: "2", name: "werk", description: "werken", imageURL: "wonen.png", components: [])
        let buildingblock6: Buildingblock = Buildingblock(id: "2", name: "werk", description: "werken", imageURL: "wonen.png", components: [])
        let buildingblock7: Buildingblock = Buildingblock(id: "2", name: "werk", description: "werken", imageURL: "wonen.png", components: [])
        let buildingblock8: Buildingblock = Buildingblock(id: "2", name: "werk", description: "werken", imageURL: "wonen.png", components: [])
        let buildingblock9: Buildingblock = Buildingblock(id: "2", name: "werk", description: "werken", imageURL: "wonen.png", components: [])
        
        let buildingblocks: [Buildingblock] = [buildingblock1, buildingblock2, buildingblock3, buildingblock4,
                                               buildingblock5, buildingblock6, buildingblock7,
                                               buildingblock8, buildingblock9]
        
        return buildingblocks
    }
}
