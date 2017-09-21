///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nl.inholland.projectapi.presentation;
//
//import java.util.ArrayList;
//import java.util.List;
//import nl.inholland.projectapi.model.OUD_user;
//import nl.inholland.projectapi.presentation.model.UserView;
//
///**
// *
// * @author student
// */
//public class UserPresenter extends BasePresenter {
//    public List<UserView> present(List<OUD_user> users) {
//        List<UserView> views = new ArrayList<>();
//        
//        for(OUD_user user : users) {
//            UserView view = new UserView();
//        
//            view.id = user.getId();
//            view.married = user.isMarried();
//            view.name = user.getName();
//            
//            views.add(view);
//        }
//        
//        return views;
//    }
//    
//    public UserView present(OUD_user user) {
//        UserView view = new UserView();
//        
//        view.id = user.getId();
//        view.married = user.isMarried();
//        view.name = user.getName();
//        
//        return view;
//    }
//}
