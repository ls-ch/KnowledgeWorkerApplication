/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.lang.Object;
import static java.time.temporal.TemporalQueries.localDate;

/**
 *
 * @author asian
 */
public class Task {
    private StringProperty task;
    private StringProperty description;
    private String doDate;
    private String dueDate;
    private StringProperty taskPriority;
    private int id;


    
   
    public Task(
            int id, 
            String task, 
            String description, 
            String doDate, 
            String dueDate, 
            String taskPriority
    ){
        this.task = new SimpleStringProperty(task);
        this.description = new SimpleStringProperty(description);
        this.taskPriority = new SimpleStringProperty(taskPriority);
        this.id = id;
        this.doDate = doDate;
        this.dueDate = dueDate;
        
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getTask(){
        return task.get();
    }
    
    public String getDescription(){
        return description.get();
    }
    //what is .get()
    public String getDoDate(){
        return doDate;
    }
    
    public String getDueDate(){
        return dueDate;
    }
    
    public String getTaskPriority(){
        return taskPriority.get();
    }
    
   public StringProperty getTaskProperty(){
       return task;
   }
   
   public void setTask(String task){
       this.task.set(task);
   }
   
   public StringProperty getDescriptionProperty(){
       return description;
   }
   
   public void setDescription(String description){
       this.description.set(description);
   }
   
   public StringProperty getTaskPriorityProperty(){
       return taskPriority;
   }
   
   public void setTaskPriority(String taskPriority){
       this.taskPriority.set(taskPriority);
   }
   
}
