package com.spa.exception;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ChainedRuntimeException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable cause = null;

    public ChainedRuntimeException() {
        super();
    }

    public ChainedRuntimeException(String msg) {
        super(msg);
    }

    public ChainedRuntimeException(String msg, Throwable cause) {
        super(msg);
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (cause != null) {
            System.err.println("Caused by:");
            cause.printStackTrace();
        }
    }

    public void printStackTrace(PrintWriter w) {
        super.printStackTrace(w);
        if (cause != null) {
            w.println("Caused by:");
            cause.printStackTrace(w);
        }
    }

    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        if (cause != null) {
            s.println("Caused by:");
            cause.printStackTrace(s);
        }
    }
}