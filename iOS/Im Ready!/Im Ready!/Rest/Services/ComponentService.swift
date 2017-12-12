//
//  ComponentService.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 22/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public class ComponentService : Service {
    func getMockComponents() -> [Component] {
        var components: [Component] = []

        components.append(Component(id: "HomeComponent", buildingblockId: "Wonen", name: "Zoek een woning", description: "Zoek een woning", youtubeUrl: "https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        components.append(Component(id: "HomeComponent", buildingblockId: "Wonen", name: "Zoek witgoed uit", description: "Zoek witgoed uit", youtubeUrl: "nein"))
        
        components.append(Component(id: "WorkComponent", buildingblockId: "Werk", name: "Zoek 3 baantjes", description: "werk", youtubeUrl: "kein youtubeurl"))
        components.append(Component(id: "WorkComponent", buildingblockId: "Werk", name: "Solliciteer voor een baan", description: "Solliciteer", youtubeUrl: "youtubeurl hier"))
        
        components.append(Component(id: "FinanceComponent", buildingblockId: "Financien", name: "Check je money, swa", description: "controller geld", youtubeUrl: ""))

        return components
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
