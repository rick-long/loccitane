package com.spa.tools.detector;


// a detector is expected to provide sub-class of this  factory which 
// will create the required detector.
// The static block without a function name in the the detector class 
// should create a new instance of its factory and add it to the
// hash-table
public abstract class DetectorFactory
{
	public abstract String getDefaultType();

	public abstract Detector create();
	public abstract Detector create(String type);
}
