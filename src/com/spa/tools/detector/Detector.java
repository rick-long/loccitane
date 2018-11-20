package com.spa.tools.detector;

import java.util.Map;

import com.spa.exception.ChainedException;

public interface Detector
{
  void description();
  String argtype_required();
  void processing(Map inputParameters, Map outParameters) throws ChainedException ;
}
