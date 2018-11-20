package com.spa.runtime.exception;

/**
 * 资源错误异常
 * 
 * @author Ivy on 2016-7-4
 *
 */
public class ResourceException extends RuntimeException{

    public static final ResourceException NOT_ENOUGH_CAPACITY = new ResourceException("Not enough capacity error!");
    
    public static final ResourceException NO_ROOM_AVAILABLE = new ResourceException("No rooms available error!");
    
    public static final ResourceException TIME_BLOCK = new ResourceException("Time has been blocked!");

    public ResourceException(String message) {
        super(message);
    }
}
