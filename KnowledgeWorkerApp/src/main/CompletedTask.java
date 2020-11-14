/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Ian_R
 */
public class CompletedTask {

    private String task;
    private int id;
    private String doDate;
    private String dueDate;
    private String desc;
    private String taskPriority;

    public CompletedTask(int id, String task, String desc, String doDate,
            String dueDate, String taskPriority) {
        this.task = task;
        this.id = id;
        this.desc = desc;
        this.doDate = doDate;
        this.dueDate = dueDate;
        this.taskPriority = taskPriority;
    }

    public void setDoDate(String doDate) {
        this.doDate = doDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTaskpriority(String taskpriority) {
        this.taskPriority = taskpriority;
    }

    public String getDoDate() {
        return doDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDesc() {
        return desc;
    }

    public String getTaskpriority() {
        return taskPriority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
