/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addquestion;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Novi
 */
public class QuestionList {
      private StringProperty question;
      private StringProperty letter;
      
      private StringProperty chA;
      private StringProperty chB;
      private StringProperty chC;
      private StringProperty chD;
     
     
    public QuestionList(String question,String letter,String chA,String chB ,String chC ,String chD)
    {
         this.question= new SimpleStringProperty(question);
         this.letter=new SimpleStringProperty(letter);
         this.chA=new SimpleStringProperty(chA);
         this.chB=new SimpleStringProperty(chB);  
        this.chC=new SimpleStringProperty(chC);
        this.chD=new SimpleStringProperty(chD);  
    }
      public StringProperty questionProperty() { return question; }
      public StringProperty letterProperty() { return letter; }
       public StringProperty chAProperty() { return chA; }
      public StringProperty chBProperty() { return chB; }
      public StringProperty chCProperty() { return chC; }
      public StringProperty chDProperty() { return chD; }
      
      
     
    
}
