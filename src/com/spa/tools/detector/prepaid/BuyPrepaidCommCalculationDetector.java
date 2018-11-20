package com.spa.tools.detector.prepaid;
import java.util.Map;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.spa.vo.prepaid.PrepaidAddVO;
import com.spa.exception.ChainedException;
import com.spa.tools.detector.Detector;
import com.spa.tools.detector.DetectorCreator;
import com.spa.tools.detector.DetectorFactory;
import com.spa.tools.drools.KnowLedgeBaseReader;

public class BuyPrepaidCommCalculationDetector implements Detector {
	private static Logger logger = Logger.getLogger(BuyPrepaidCommCalculationDetector.class);
	private String type;

	public BuyPrepaidCommCalculationDetector() {
		type = new String("getBuyPrepaidCommCalculationDetector");
	}

	public BuyPrepaidCommCalculationDetector(String t) {
		type = t;
	}

	public void description() {
		System.out.print("This is a description of the BuyPrepaidCommCalculationDetector plug in.");
		System.out.println(" It is a " + type + " detector");
	}

	public String argtype_required() {
		return type;
	}

	public void processing(Map inputParameters, Map outputParameters) throws ChainedException {
		logger.debug(" entering BuyPrepaidCommCalculationDetector ........... ");
		//利用map可以储存多个对象
		PrepaidAddVO prepaidAddVO=(PrepaidAddVO) inputParameters.get("prepaidAddVO");
		
		String drlFile="drl/BuyPrepaidCommCalculation.drl";
		
    	if(prepaidAddVO!=null){
	    	try {
				KnowledgeBase kbase =KnowLedgeBaseReader.readKnowledgeBaseByDRL(drlFile);
				StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
				//将对象插入
				ksession.insert(prepaidAddVO);
				//执行
				ksession.fireAllRules();
				ksession.dispose();
				//返回
				outputParameters.put("commissionRate", prepaidAddVO.getCommissionRate());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
	}
	

	// This class has to be static (i.e. a class method rather than
	// an object one) because it's called from the static block below
	static class BuyPrepaidCommCalculationDetectorFactory extends DetectorFactory {
		public String getDefaultType() {
			return "getBuyPrepaidCommCalculationDetector";
		}
		public Detector create() {
			return create(getDefaultType());
		}
		public Detector create(String type) {
			return (new BuyPrepaidCommCalculationDetector(type));
		}
	}

	// Note that the following routine is static and has no name, which
	// means it will only be run when the class is loaded
	static {
		// put factory in the hashtable for detector factories.
		DetectorCreator.detectorFactories.put(
				BuyPrepaidCommCalculationDetector.class.getName(),
				new BuyPrepaidCommCalculationDetectorFactory());
	}

}
