package com.dibyendu.demoeventmanagement.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response <T>{
    private int status;
    private T data;
}
