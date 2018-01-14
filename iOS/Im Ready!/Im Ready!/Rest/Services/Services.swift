//
//  Services.swift
//  Im Ready!
//
//  Created by Wouter Vermeij on 17/11/2017.
//  Copyright © 2017 Wouter Vermeij. All rights reserved.
//

import Foundation

public let authenticationService: AuthenticationService = AuthenticationService()
public let clientService: ClientService = ClientService()
public let buildingblockService: BuildingblockService = BuildingblockService()
public let componentService: ComponentService = ComponentService()
public let activityService: ActivityService = ActivityService()
public let chatsService: ChatsService = ChatsService()
public let messageService: MessageService = MessageService()
public let userService: UserService = UserService()
public let appointmentService: AppointmentService = AppointmentService()

public class Service {
    let apiClient : ApiClient
    
    init(){
        apiClient = ApiClient()
    }
}
