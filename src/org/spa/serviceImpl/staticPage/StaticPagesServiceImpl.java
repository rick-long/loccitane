package org.spa.serviceImpl.staticPage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.staticPage.StaticPages;
import org.spa.service.staticPage.StaticPagesService;
import org.springframework.stereotype.Service;

/**
 * 
 * Created by Ivy on 2016/01/16.
 *
 */
@Service
public class StaticPagesServiceImpl extends BaseDaoHibernate<StaticPages> implements StaticPagesService{

	
	public StaticPages getPage(String url, String parameter) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StaticPages.class);
		criteria.add(Restrictions.eq("url", url));
		if (StringUtils.isNoneBlank(parameter)) {
			criteria.add(Restrictions.eq("parameter", parameter));
		}else{
			criteria.add(Restrictions.isNull("parameter"));
		}
		return get(criteria);
	}

	public StaticPages crawler(String url, String parameter, StaticPages staticPages) {
		String crawlerUrl = url.replaceFirst("staticPage", "crawlerPage");
		if (StringUtils.isNoneBlank(parameter)) {
			crawlerUrl += "?" + parameter;
		}
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(crawlerUrl);
		HttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream is = null;
			try {
				is = response.getEntity().getContent();
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = -1;
			try {
				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String result = baos.toString();
			if (staticPages == null) {
				staticPages = new StaticPages();
				staticPages.setUrl(url);
				staticPages.setParameter(parameter);
			}
			staticPages.setContent(result);
			staticPages.setDate(new Date());
			this.saveOrUpdate(staticPages);
		}

		return staticPages;
	}
}
