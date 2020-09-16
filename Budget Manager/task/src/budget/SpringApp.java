package budget;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApp implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        final var context = new ClassPathXmlApplicationContext("application-context.xml");
        final var application = context.getBean("app", Application.class);
        application.run();
    }
}
