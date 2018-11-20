package com.spa.tools.drools;

import java.io.StringReader;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnowLedgeBaseReader {

    protected static final Logger LOGGER = LoggerFactory.getLogger(KnowLedgeBaseReader.class);

	public KnowLedgeBaseReader() {
		// super
	}
	
	
	public static KnowledgeBase readKnowledgeBaseByDRL(String drlFile) throws Exception {
		   
	      KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	      kbuilder.add(ResourceFactory.newClassPathResource(drlFile),ResourceType.DRL);
	      
	      KnowledgeBuilderErrors errors = kbuilder.getErrors();
	      
	      if (errors.size() > 0) {
	         for (KnowledgeBuilderError error: errors) {
	            System.err.println(error);
	         }
	         throw new IllegalArgumentException("Could not parse knowledge.");
	      }
	      
	      KnowledgeBaseConfiguration kbConf = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
	      kbConf.setProperty( "org.drools.sequential", "true");
	      KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(kbConf);
	      kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	      
	      return kbase;
	}
	
	public static KnowledgeBase readKnowledgeBaseByListDrl(List<String> drlFiles) throws Exception {
		   
	      KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	      if(drlFiles !=null && drlFiles.size()>0){
	    	  for(String drlFile : drlFiles ){
	    		  kbuilder.add(ResourceFactory.newClassPathResource(drlFile), ResourceType.DRL);
	    	  }
	      }
	      
	      KnowledgeBuilderErrors errors = kbuilder.getErrors();
	      
	      if (errors.size() > 0) {
	         for (KnowledgeBuilderError error: errors) {
	            System.err.println(error);
	         }
	         throw new IllegalArgumentException("Could not parse knowledge.");
	      }
	      
	      KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
	      kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	      
	      return kbase;
	}

    public static StatefulKnowledgeSession getSession(String drlContent) {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();

        Resource myResource = ResourceFactory.newReaderResource(new StringReader(drlContent));
        kbuilder.add(myResource, ResourceType.DRL);
        // Check the builder for errors
        if (kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors().toString());
            throw new RuntimeException("Unable to compile drl\".");
        }
        // get the compiled packages (which are serializable)
        // add the packages to a knowledgebase (deploy the knowledge packages).
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        StatefulKnowledgeSession session = kbase.newStatefulKnowledgeSession();
        session.setGlobal("logger", LOGGER);
        return session;
    }

}
