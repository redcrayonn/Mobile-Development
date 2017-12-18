//
//  BuildingblockService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 20/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation
import Alamofire

public class BuildingblockService : Service {
    func getMockBuildingblocks() {
        var buildingblocks: [Buildingblock] = []
        
        buildingblocks.append(Buildingblock(id: "1", name: "Wonen", description: "Lekker wonen", blockType: 0))
        buildingblocks.append(Buildingblock(id: "2", name: "Werken", description: "Lekker werken", blockType: 5))
    }
    
    func getBuildingblocks(onSuccess: @escaping ([Buildingblock]) -> (),
                           onFailure: @escaping () -> ()){
        apiClient.send(toRelativePath: "buildingblock",
            withHttpMethod: .get,
            onSuccessParser: { (response) in
                
                var buildingblocks: [Buildingblock] = []
                
                do {
                    // Decode json to a list of buildingblocks
                    let buildingblockResult = try JSONDecoder().decode([Buildingblock].self, from: response )
                    
                    // Add the buildingblocks to a list and return the list
                    for buildingblock in buildingblockResult {
                        buildingblocks.append(buildingblock)
                    }
                    onSuccess(buildingblocks)
                    
                } catch {
                    onFailure()
                }
        }) {
            print("failed")
            onFailure()
        }
    }
    
//    func getBuildingblock(withId id: String,
//                          onSuccess: @escaping () -> (),
//                          onFailure: @escaping () -> () -> ()) {
//        apiClient.send(
//            toRelativePath: "buildingblock/\(id)",
//            withHttpMethod: .get,
//            onSuccessParser: { (_ data) in
//            print(data)
//            onSuccess()
//        }) {
//            onFailure()
//        }
//    }
}
