package com.spa.exception;

public class DetectorNotCreatedException extends Exception
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DetectorNotCreatedException()
   {
      super("Detector could not be created!");
   }
}

