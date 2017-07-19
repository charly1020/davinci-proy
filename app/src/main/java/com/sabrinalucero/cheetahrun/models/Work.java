package com.sabrinalucero.cheetahrun.models;

/**
 * Created by Sabrina on 19/07/2017.
 */

public class Work {

  private int id;
  private String name;

  public Work(){

  }
  public Work(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private String description;


}
