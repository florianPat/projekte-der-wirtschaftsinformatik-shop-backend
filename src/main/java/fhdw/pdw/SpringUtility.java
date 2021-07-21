package fhdw.pdw;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtility implements ApplicationContextAware {

  static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringUtility.setContext(applicationContext);
  }

  public static <T> T getBean(final Class<T> clazz) {
    return SpringUtility.applicationContext.getBean(clazz);
  }

  public static ApplicationContext getContext() {
    return SpringUtility.applicationContext;
  }

  public static void setContext(ApplicationContext context) {
    SpringUtility.applicationContext = context;
  }
}
