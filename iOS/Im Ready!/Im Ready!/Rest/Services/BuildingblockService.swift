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
}
