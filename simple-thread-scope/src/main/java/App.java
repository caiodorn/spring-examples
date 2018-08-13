import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main (String[] args) {

        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(Conf.class);
        context.refresh();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                context.getBean(Bean.class);
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                context.getBean(Bean.class);
            }
        };

        Thread t3= new Thread() {
            @Override
            public void run() {
                context.getBean(Bean.class);
            }
        };

        t1.start();
        t2.start();
        t3.start();

    }

}
