/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emujava;

import java.util.*;

/**
 * @author jBillu
 * @version 1.0 August 23, 2013
 */

public class MemberMethod {
    
    private ArrayList<Token> methodTokens;
    private ArrayList<Statement> statementsList;
    
    public MemberMethod() {
        methodTokens = new ArrayList<Token>();
        statementsList = new ArrayList<Statement>();
    } //END MemberMethod() CONSTRUCTOR
    
    public String getMethodHeader() {
        String header = "";
        int ct = 0;
        DO1:
        do {
            Token token = (Token) methodTokens.get(ct++);
            if (token.getToken().equals("[") || token.getToken().equals("]") || token.getToken().equals("(") || token.getToken().equals(".") || token.getToken().equals(",")) {
                header = header + token.getToken();
            } else if (token.getToken().equals(")")) {
                if (header.endsWith("(")) {
                    header = header + token.getToken();
                } else {
                    header = header + " " + token.getToken();
                } //END if-else STATEMENT
            } else {
                header = header + " " + token.getToken();
            } //END if-else STATEMENT
            if (token.getToken().equals("{")) {
                break DO1;
            } //END if STATEMENT
        } while (ct < methodTokens.size());
        return header;
    } //END getMethodHeader() METHOD
    
    public int getMethodHeaderCount() {
        int count = 0;
        DO1:
        do {
            Token token = (Token) methodTokens.get(count++);
            if (token.getToken().equals("{")) {
                break DO1;
            } //END if STATEMENT
        } while (count < methodTokens.size());
        
        return count;
    } //END getMethodHeaderCount() METHOD
    
    public String getAnnotatedMethodHeader() {
        StringBuilder header = new StringBuilder();
        int ct = 0;
        DO1:
        do {
            Token token = (Token) methodTokens.get(ct++);
            if (token.getToken().equals("[") || token.getToken().equals("]") || token.getToken().equals(")") || token.getToken().equals(".")) {
                header.append(token.getToken());
            } else if (token.getToken().equals("(")) {
                header.append("_T").append(token.getToken());
            } else {
                header.append(" ").append(token.getToken());
            } //END if-else STATEMENT
            if (token.getToken().equals("{")) {
                break DO1;
            } //END if STATEMENT
        } while (ct < methodTokens.size());
        return header.toString();
    } //END getAnnotatedMethodHeader() METHOD
    
    public void identifyStatements() {
        int ct = 0;
        do {
            Token token = methodTokens.get(ct++);
            if (token.getToken().equals("{")) {
                break;
            }
        } while (ct < methodTokens.size());
        ct--;
        for (; ct < methodTokens.size() - 1; ct++) {
            Token token = (Token) methodTokens.get(ct);
            if (token.getToken().equals("if")) {
                Statement statement = new Statement();
                do {
                    statement.addToken(token);
                    // Error here
                    token = (Token) methodTokens.get(++ct);
                } while (!token.getToken().equals("{") && ct + 1 < methodTokens.size());
                ct--;
                statement.setDescription("If Statement");
                statementsList.add(statement);
            } else if (token.getToken().equals("else")) {
                Statement statement = new Statement();
                statement.addToken(token);
                statement.setDescription("Else Statement");
                statementsList.add(statement);
            } else if (token.getToken().equals("while")) {
                Statement statement = new Statement();
                do {
                    statement.addToken(token);
                    token = (Token) methodTokens.get(++ct);
                } while (!token.getToken().equals("{"));
                ct--;
                statement.setDescription("While Statement");
                statementsList.add(statement);
            } else if (token.getToken().equals("this")) {
                Statement statement = new Statement();
                do {
                    statement.addToken(token);
                    token = (Token) methodTokens.get(++ct);
                } while (!token.getToken().equals(";"));
                statement.addToken(token);
                if (((Token) statement.getStatementTokens().get(3)).getToken().equals("(")) {
                    statement.setDescription("Function Call");
                } else {
                    statement.setDescription("Data Member Initialization");
                } //END if-else STATEMENT
                statementsList.add(statement);
            } else if (token.getDescription().equals("Data Type")) {
                Statement statement = new Statement();
                do {
                    statement.addToken(token);
                    token = (Token) methodTokens.get(++ct);
                } while (!token.getToken().equals(";"));
                statement.addToken(token);
                statement.setDescription("Local Declaration");
                statementsList.add(statement);
            } else if (token.getDescription().equals("Identifier")) {
                System.out.println("Token: " + token.getToken());
                Statement statement = new Statement();
                do {
                    statement.addToken(token);
                    token = (Token) methodTokens.get(++ct);
                    System.out.println("Token: " + token.getToken());
                } while (!token.getToken().equals(";") && ct + 1 < methodTokens.size());
                statement.addToken(token);
                if (statement.getStatementTokens().get(2).getToken().equals("new")) {
                    statement.setDescription("Object Creation");
                } else if (statement.getStatementTokens().get(3).getToken().equals("new")) {
                    statement.setDescription("Object Creation");
                } else if (statement.getStatementTokens().get(1).getToken().equals("(")) {
                    statement.setDescription("Function Call");
                } else if (statement.getStatementTokens().get(3).getToken().equals("(")) {
                    statement.setDescription("Function Call");
                } else if (statement.getStatementTokens().get(3).getToken().equals("=")) {
                    statement.setDescription("Data Member Initialization");
                } else {
                    statement.setDescription("Assignment Statement");
                } //END if-else STATEMENTS
                statementsList.add(statement);
            } else if (token.getToken().equals("return")) {
                Statement statement = new Statement();
                do {
                    statement.addToken(token);
                    token = (Token) methodTokens.get(++ct);
                } while (!token.getToken().equals(";"));
                statement.addToken(token);
                statement.setDescription("Return Statement");
                statementsList.add(statement);
            } else if (token.getToken().equals("{")) {
                Statement statement = new Statement();
                statement.setDescription("Starting Delemeter");
                statement.addToken(token);
                statementsList.add(statement);
            } else if (token.getToken().equals("}")) {
                Statement statement = new Statement();
                statement.setDescription("Ending Statement");
                statement.addToken(token);
                statementsList.add(statement);
            } //END if-else STATEMENT
        } //END for LOOP
    } //END identifyStatements() METHOD
    
    public String getMethodName() {
        String methodName = "";
        for (int mt = 0; mt < methodTokens.size(); mt++) {
            Token token = (Token) methodTokens.get(mt);
            if (token.getDescription().equals("Identifier")) {
                Token token2 = (Token) methodTokens.get(mt + 1);
                if (token2.getToken().equals("(")) {
                    return token.getToken();
                } //END if STATEMENT
            } //END if STATEMENT
        } //END for LOOP
        return methodName;
    } //END getMethodName() METHOD
    
    public void addToken(Token token) {
        methodTokens.add(token);
    } //END addToken() METHOD
    
    public ArrayList<Token> getMethodTokens() {
        return methodTokens;
    } //END getMethodTokens() METHOD
    
    public ArrayList<Statement> getStatementsList() {
        return statementsList;
    } //END getStatementsList() METHOD
    
    public void printStatements() {
        for (Statement statement : statementsList) {
            System.out.println(statement.getDescription());
            ArrayList<Token> tokens = statement.getStatementTokens();
            for (Token token : tokens) {
                System.out.print(token.getToken() + " ");
            } //END for LOOP
            System.out.println();
        } //END for LOOP
    } //END printStatements() METHOD
    
} //END MemberMethod CLASS
