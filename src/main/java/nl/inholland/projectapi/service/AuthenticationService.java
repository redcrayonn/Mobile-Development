///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nl.inholland.projectapi.service;
//
//import io.dropwizard.auth.AuthenticationException;
//import io.dropwizard.auth.Authenticator;
//import io.dropwizard.auth.basic.BasicCredentials;
//import java.util.Optional;
//import javax.inject.Inject;
//import nl.inholland.projectapi.model.OUD_user;
//import nl.inholland.projectapi.persistence.UserDAO;
//
///**
// *
// * @author student
// */
//public class AuthenticationService extends BaseService implements Authenticator<BasicCredentials, OUD_user>{
//   
//    private UserDAO dao;
//    
//    @Inject
//    public AuthenticationService(UserDAO dao) {
//        this.dao = dao;
//    }
//    
//    @Override
//    public Optional<OUD_user> authenticate(BasicCredentials credentials) throws AuthenticationException {
//        OUD_user user = dao.getUserByName(credentials.getUsername());
//        
//        if(user == null) {
//            return Optional.empty();
//        }
//        
//        if(user.getPassword().equals(credentials.getPassword())) {
//            return Optional.of(user);
//        } 
//        
//        return Optional.empty();
//    }
//}