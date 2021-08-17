package com.iths.jh.RecipeApplication.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ServiceResponse<T> {
    private boolean sucessful;
    private List<T> responseObjects;
    private T responseObject;
    private List<String> errorMessages;

    public ServiceResponse() {
        this.responseObjects = new ArrayList<T>();
        this.errorMessages = new ArrayList<>();
    }

    public ServiceResponse(List<T> responseObjects, List<String> errorMessages) {
        this.responseObjects = responseObjects;
        this.errorMessages = errorMessages;
        this.sucessful = CollectionUtils.isEmpty(errorMessages);
    }
    public ServiceResponse(T responseObject, List<String> errorMessages) {
        this.responseObject = responseObject;
        this.errorMessages = errorMessages;
        this.sucessful = CollectionUtils.isEmpty(errorMessages);
    }

    public void addErrorMessage(String error) {
        sucessfulFalse();
        this.errorMessages.add(error);
    }

    public void setResponseObject(T object) {
        sucessfulTrue();
        this.responseObject = object;
    }

    public void setResponseObjects(List<T> objects) {
        sucessfulTrue();
        this.responseObjects = objects;
    }

    public void setErrorMessages(List<String> errorMessages) {
        if (!errorMessages.isEmpty()) sucessfulFalse();
        this.errorMessages = errorMessages;
    }

    private void sucessfulTrue() {
        this.sucessful = true;
    }

    private void sucessfulFalse() {
        this.sucessful = false;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ServiceResponse{");
        sb.append("sucessful=").append(sucessful);
        sb.append(", responseObjects=").append(responseObjects);
        sb.append(", responseObject=").append(responseObject);
        sb.append(", errorMessages=").append(errorMessages);
        sb.append('}');
        return sb.toString();
    }
}