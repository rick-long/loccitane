package org.spa.utils;



import java.util.regex.Matcher;

import java.util.regex.Pattern;



import org.apache.commons.lang.StringUtils;



public class EmailUtils {



	/**

	 * check the fomart of email address, should contain '@' and '.'

	 * @param emailAddress

	 */

	public static boolean checkEmailAddressFormat(String emailAddress) {



	      //Set the email pattern string

	      Pattern p = Pattern.compile("^([\\w\\-\\.]+)@((\\[([0-9]{1,3}\\.){3}[0-9]{1,3}\\])|(([\\w\\-]+\\.)+)([a-zA-Z]{2,4}))$");



	      //Match the given string with the pattern

	      Matcher m = p.matcher(emailAddress);



	      //check whether match is found 

	      boolean matchFound = m.matches();



	      if (matchFound)

	        return true;

	      else

	        return false;

    }	



	public static String getEmailSenderInformation(String name, String email) {

		

		StringBuffer buff = new StringBuffer();

		

		if (StringUtils.isNotEmpty(name)) {

			buff.append(name).append(" <");

		}



		buff.append(email);



		if (StringUtils.isNotEmpty(name)) {

			buff.append(">");

		}

		

		return buff.toString();

		



	}

}