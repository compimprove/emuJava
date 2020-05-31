/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.util.*;

/**
 *
 * @author jBillu
 * 
 * @version 1.0 August 23, 2013
 */
public class Statement {
    
    private ArrayList<Token> statementTokens = new ArrayList<Token>();
    private String description = "";
    
    public void addToken( Token token ) {
        statementTokens.add( token );
    } //END addToken() METHOD
    
    public ArrayList<Token> getStatementTokens() {
        return statementTokens;
    } //END getStatementTokens() METHOD

    public void setDescription( String desc ) {
        description = desc;
    } //END setDescription() METHOD
    
    public String getDescription() {
        return description;
    } //END getDescription() METHOD
    
}
