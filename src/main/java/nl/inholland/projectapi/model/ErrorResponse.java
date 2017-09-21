/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 *
 * @author student
 */
public class ErrorResponse{
   private int errorCode;
        
    private String userMessage;
    
    private String internalMessage;
    private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    
    public ErrorResponse(int errorCode, String userMessage, String internalMessage)
    {
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.internalMessage = internalMessage;
    }
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getInternalMessage() {
        return internalMessage;
    }

    public void setInternalMessage(String internalMessage) {
        this.internalMessage = internalMessage;
    }
     
}
