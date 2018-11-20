package org.spa.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.spa.exception.ChainedException;
import com.spa.exception.ChainedRuntimeException;
import com.spa.exception.DetectorNotCreatedException;
import com.spa.tools.detector.Detector;
import com.spa.tools.detector.DetectorCreator;

public class DetectorUtil {

	private final static Logger logger = Logger.getLogger(DetectorUtil.class);

	final public static void processing(final String classNameList, Map inputParameters, Map outputParameters) 
	throws ChainedException {
		
		Detector [] plugins = null;

		// load plugin classes
		if(classNameList !=null && StringUtils.isNotBlank(classNameList)){
			String className = null;
			try {
	    		String [] classnames = classNameList.split(",");
	    		plugins = new Detector[classnames.length];

	    		for (int i=0; i<classnames.length; i++) {
	    			className = classnames[i];
	    			Detector s = DetectorCreator.createDetector(className);
	    			plugins[i] = s;
	    		}
			}
			catch (DetectorNotCreatedException e) {
				logger.error(className+" not found", e);
				e.printStackTrace(System.err);
				throw new ChainedRuntimeException("Detector Plugin Not Found, "+className+".");
			}
    	}

		// execute plugins
		if (plugins != null) {
    		for (int i=0; i<plugins.length; i++) {
    			plugins[i].processing(inputParameters, outputParameters);
    		}
		}

	}

}
