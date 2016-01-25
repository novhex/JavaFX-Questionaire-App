/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DB;

/**
 *
 * @author Novi
 */
public class Queries {
 
    
    public static String SELECT_ALL_FROM_QUESTIONS(){
    
        return "Select * from question";
    }
    
    public static String SELECT_SPECIFIC_RECORD() {
        
        return "Select * from question where id=?";
    }
    
    public static String INSERT_NEW_QUESTION_QUERY() {
    
        return "";
    }
    
    public static String SETTINGS_GAMETIME_LIMIT() {
    
        return "SELECT cfg_value from cfg where cfg_id=1";
    }
    public static String LOGIN_QUERY() {
        return "SELECT * from accounts where username=? AND password=md5(?)";
    }
}
