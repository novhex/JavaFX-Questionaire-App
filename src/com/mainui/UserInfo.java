/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainui;

/**
 *
 * @author Novi
 */
public class UserInfo {
    private String currUsername;
  private String currAccess;
  
  public UserInfo(){
  
  }
    public String getCurrUsername() {
        return currUsername;
    }

    public void setCurrUsername(String currUsername) {
        this.currUsername = currUsername;
    }

    public String getCurrAccess() {
        return currAccess;
    }

    public void setCurrAccess(String currAccess) {
        this.currAccess = currAccess;
    }
  
    
}
