///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nl.inholland.projectapi.resource;
//
//import io.dropwizard.auth.Auth;
//import java.util.List;
//import javax.inject.Inject;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Produces;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.core.MediaType;
//import nl.inholland.projectapi.presentation.model.UserView;
//import nl.inholland.projectapi.presentation.UserPresenter;
//import nl.inholland.projectapi.service.UserService;
//import nl.inholland.projectapi.model.OUD_user;
//
///**
// *
// * @author student
// */
//@Path("/user")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public class UserResource extends BaseResource {
//    private final UserService userService;
//    private final UserPresenter userPresenter;
//    
//    @Inject
//    public UserResource(UserService userService, UserPresenter userPresenter) {
//        this.userService = userService;
//        this.userPresenter = userPresenter;
//    }
//    
//    @GET
//    public List<UserView> getAll(@Auth OUD_user user) {
//        return null;
//    }
//    
//    @GET
//    @Path("/{userId}")
//    public UserView get(@PathParam("userId")String userId) {
//        OUD_user user = userService.getUserById(userId);
//        return userPresenter.present(user);
//    }
// 
//    @POST
//    public void create(OUD_user user) {
//        //
//    }
//}
//
