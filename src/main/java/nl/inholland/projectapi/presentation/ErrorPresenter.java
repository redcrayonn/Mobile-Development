/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.presentation;

import nl.inholland.projectapi.model.ErrorResponse;
import nl.inholland.projectapi.presentation.model.ErrorView;

/**
 *
 * @author student
 */
public class ErrorPresenter extends BasePresenter{
    public String present(ErrorResponse error) {
        ErrorView view = new ErrorView();
        
        view.errorCode = error.getErrorCode();
        view.internalMessage = error.getInternalMessage();
        view.userMessage = error.getUserMessage();
        return super.toJson(view);
    }
    
}
