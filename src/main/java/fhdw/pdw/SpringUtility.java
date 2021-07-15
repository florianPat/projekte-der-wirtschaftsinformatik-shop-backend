package fhdw.pdw;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtility implements ApplicationContextAware {

  @Autowired static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringUtility.applicationContext = applicationContext;
  }

  public static <T> T getBean(final Class clazz) {
    return (T) SpringUtility.applicationContext.getBean(clazz);
  }

  public static ApplicationContext getContext() {
    return SpringUtility.applicationContext;
  }
}
