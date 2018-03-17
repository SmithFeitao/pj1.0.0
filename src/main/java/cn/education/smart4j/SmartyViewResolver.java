package cn.education.smart4j;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/**
 * 基于smarty模板的视图解决器。
 * @author znyin
 *
 */
public class SmartyViewResolver extends AbstractTemplateViewResolver{
	
	/**
	 * 构造函数。
	 */
	public SmartyViewResolver() {
		setViewClass(requiredViewClass());
	}

	@Override
	protected Class<?> requiredViewClass() {
		return SmartyView.class;
	}

}
