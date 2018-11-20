package org.spa.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
/**
 * Created by Ivy on 2016/03/13.
 */
public class I18nUtil {
	public static String getMessageKey(String key) {
		Locale locale = LocaleContextHolder.getLocale();
		MessageSource messageSource = (MessageSource) SpringUtil.getBean("messageSource");

		DefaultMessageSourceResolvable resolvable = new DefaultMessageSourceResolvable(key);
		try {
			key = messageSource.getMessage(resolvable, locale);
		} catch (NoSuchMessageException e) {

		}
		return key;
	}
}
