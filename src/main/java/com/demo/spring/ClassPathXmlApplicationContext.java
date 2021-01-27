package com.demo.spring;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 管理器
 * 
 * @author jerome
 */
public class ClassPathXmlApplicationContext implements BeanFactory {

	private Map<String, Object> beans = new HashMap<String, Object>();

	/**
	 * 初始化配置文件
	 * 模拟spring配置文件。模拟大的工厂，把东西都写到大的配置文件，通过代码读取xml文件模拟spring。
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ClassPathXmlApplicationContext() throws Exception {
		System.out.println("ClassPathXmlApplicationContext init.");
		SAXBuilder sb = new SAXBuilder();

		Document doc = sb.build(this.getClass().getClassLoader().getResourceAsStream("beans.xml")); // 构造文档对象
		Element root = doc.getRootElement(); // 获取根元素HD
		List<Element> list = root.getChildren("bean");// 取名字为bean的所有元素

		// 循环每个bean
		for (int i = 0; i < list.size(); i++) {
			Element element = list.get(i);
			String id = element.getAttributeValue("id");
			String clazz = element.getAttributeValue("class");
			Object o = Class.forName(clazz).newInstance();
			System.out.println("ClassPathXmlApplicationContext bean's id = " + id);
			System.out.println("ClassPathXmlApplicationContext bean's class = " + clazz);
			beans.put(id, o);

			// 循环bean下的property
			for (Element propertyElement : (List<Element>) element.getChildren("property")) {
				// 调用 setUserDAO 设置 userDao的值
				String name = propertyElement.getAttributeValue("name"); // userDAO
				String bean = propertyElement.getAttributeValue("bean"); // u
				System.out.println("ClassPathXmlApplicationContext bean's property name = " + name);
				System.out.println("ClassPathXmlApplicationContext bean's property bean = " + bean);

				Object beanObject = beans.get(bean);// UserDAOImpl instance

				// userDAO -> setUserDao
				String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

				Method m = o.getClass().getMethod(methodName, beanObject.getClass().getInterfaces()[0]);
				m.invoke(o, beanObject);
				System.out.println("ClassPathXmlApplicationContext bean's property invoke method " + methodName);
			}

			System.out.println("-------------------------");
		}

	}

	public Object getBean(String id) {
		return beans.get(id);
	}

}
