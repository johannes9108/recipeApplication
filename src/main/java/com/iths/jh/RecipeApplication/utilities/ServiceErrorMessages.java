package com.iths.jh.RecipeApplication.utilities;

public enum ServiceErrorMessages {
    RECIPE("recipe"),
    USER("user"),
    FOODCATEGORY("foodcategory"),
    INGREDIENT("ingredient");


    String errorMessage;

    ServiceErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String doesNotExist(long id) {
        return errorMessage + " with id: " + id +" does not exist";
    }
    public String doesNotExist() {
        return errorMessage +" does not exist";
    }
    public String couldNotCreate(long id) {
        return "could not create " + errorMessage + " with id: " + id;
    }
    public String couldNotCreate() {
        return "could not create " + errorMessage;
    }
    public String couldNotUpdate(long id) {
        return "could not update " + errorMessage + " with id: " + id;
    }
    public String couldNotUpdate() {
        return "could not update " + errorMessage;
    }
    public String couldNotPatch(long id) { return "could not patch " + errorMessage + " with id: " + id; }
    public String couldNotPatch() {return "could not patch " + errorMessage; }
    public String couldNotFind(long id) {
        return "could not find" + errorMessage + "with id: " + id;
    }
    public String couldNotFind() {
        return "could not find" + errorMessage;
    }
}