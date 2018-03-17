package cn.education.smart4j;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.Engine;
import org.lilystudio.smarty4j.Template;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.AbstractTemplateView;

/**
 * 基于smarty模板的视图。
 *
 * @author znyin
 */
public class SmartyView extends AbstractTemplateView {

	/**
	 *
	 */
	private static String webRootPath;

	/**
	 * smarty的模板引擎
	 */
	private static Engine smartyEngine;

	/**
	 * cas_url
	 */
	private static String casUrl;

	/**
	 * web title
	 */
	private static String webTitlePre;

	/**
	 *
	 */
	private static String webTitleAfter;
	/**
	 * 是否已经初始化好了。
	 */
	private static boolean isInited = false;

	/**
	 * jcAppKey
	 */
	private static String jcAppKey;

	/**
	 * jcAppKey
	 */
	private static String taAppId;

	/**
	 * 初始化线程锁
	 */
	private static Object lock = new Object();

	/**
	 * 自适应
	 */
	public static String zlsRootUrl;

	/**
	 * 题库组卷
	 */
	public static String errorTopicUrl;


	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!isInited) {
			synchronized (lock) {
				if (!isInited) {
					init(request);
				}
			}
		}

		Template template = smartyEngine.getTemplate(this.getUrl());
		Context context = new Context();
		for (Entry<String, Object> entry : model.entrySet()) {
			context.set(entry.getKey(), entry.getValue());
		}

		response.setCharacterEncoding(smartyEngine.getEncoding());
		template.merge(context, response.getOutputStream());
	}

	/**
	 * 初始化。
	 *
	 * @param request HttpServletRequest
	 */
	synchronized private void init(HttpServletRequest request) {
		extractConfigs(request);
		autodetectEngine();

		isInited = true;
	}

	/**
	 * 从spring的ApplicationContext中得到smarty的模板引擎。
	 */
	private void autodetectEngine() {
		try {
			if (smartyEngine == null) {
				smartyEngine = BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(), Engine.class, true, false);
				smartyEngine.setTemplatePath(webRootPath + smartyEngine.getTemplatePath());
			}
		} catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException("Must define a single org.lilystudio.smarty4j.Engine bean in this web application context "
					+ "(may be inherited): org.lilystudio.smarty4j.Engine is the usual implementation. " + "This bean may be given any name.", ex);
		}
	}

	/**
	 * 提取一些配置信息，放到smarty的context中。
	 *
	 * @param request HttpServletRequest
	 */
	private void extractConfigs(HttpServletRequest request) {
		if (webRootPath == null) {
			webRootPath = request.getSession().getServletContext().getRealPath("/");
		}
		
//		if (casUrl == null) {
//			casUrl = CASRestful.getProperties().getProperty("ssoservice.cas.url");
//		}
//
//		if (webTitlePre == null) {
//			webTitlePre = (String) getApplicationContext().getBean("webTitlePre");
//		}
//
//		if (webTitleAfter == null) {
//			webTitleAfter = (String) getApplicationContext().getBean("webTitleAfter");
//		}
//
//		if (taAppId == null) {
//			taAppId = (String) getApplicationContext().getBean("taAppId");
//		}
//
//		if (jcAppKey == null) {
//			jcAppKey = (String) getApplicationContext().getBean("jcAppKey");
//		}
//
//		if (isDev == null) {
//			isDev = (Boolean) getApplicationContext().getBean("isdev");
//		}
//		
//		if (zlsRootUrl == null) {
//			zlsRootUrl = (String) getApplicationContext().getBean("zlsRootUrl");
//		}
//
//		if (errorTopicUrl == null) {
//			errorTopicUrl = (String) getApplicationContext().getBean("errorTopicUrl");
//		}
	}
}
