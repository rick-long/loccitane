package org.spa.service.staticPage;

import org.spa.model.staticPage.StaticPages;

/**
 * 
 * Created by Ivy on 2016/01/16.
 *
 */

public interface StaticPagesService {

	public StaticPages getPage(String url, String parameter);

	public StaticPages crawler(String url, String parameter, StaticPages staticPages);
}
