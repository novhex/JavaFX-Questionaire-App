/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Novi
 */
public class TestClass {
     private StringProperty que;
     
    public TestClass(String que)
    {
         this.que= new SimpleStringProperty(que);
    }
      public StringProperty queProperty() { return que; }
    
}
