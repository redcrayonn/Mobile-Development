/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;

/**
 *
 * @author student
 */
@Singleton
public class BasePresenter {
        private final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        
        protected String toJson(Object o)
        {
            String s = null;
            try {
                s = ow.writeValueAsString(o);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(BasePresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
            return s;
        }
}
