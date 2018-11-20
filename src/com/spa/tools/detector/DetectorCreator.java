package com.spa.tools.detector;

import java.util.Hashtable;

import org.springframework.util.StringUtils;

import com.spa.exception.ChainedRuntimeException;
import com.spa.exception.DetectorNotCreatedException;

public class DetectorCreator {
	// This is the list of loaded plug-ins
	public static Hashtable detectorFactories = new Hashtable();

	public static Detector createDetector(String name, String type)
			throws DetectorNotCreatedException {
		System.err.println("DetectorCreator create :: name = " + name);
		DetectorFactory s = (DetectorFactory) detectorFactories.get(name);
		if (s == null) // detector not found
		{
			try {
				if (name.indexOf(".") != -1) {
					// Loading the class should add it to the detectorFactories table.
					Class.forName(name);
					s = (DetectorFactory) detectorFactories.get(name);
				}
				if (s == null) {
					throw (new ChainedRuntimeException());
				}
				else {
					if (StringUtils.isEmpty(type)) {
						type = s.getDefaultType();
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace(System.err);
				// We'll throw an exception to indicate that
				// the detector could not be created
				throw (new ChainedRuntimeException());
			}
		}
		return (s.create(type));
	}

	public static Detector createDetector(String name)
			throws DetectorNotCreatedException {
		return createDetector(name, null);
	}

}
