package com.caiodorn.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class ThreadScopedTest {

    private AnnotationConfigApplicationContext context;
    private static final int THREAD_COUNT = 3;

    @Before
    public void setup() {
        context = new AnnotationConfigApplicationContext();
        context.register(SpringConfiguration.class);
        context.refresh();
    }

    @Test
    public void whenThreadScopedBean_thenEachThreadShouldGetItsOwnObject() throws InterruptedException {

        Set<Integer> hashCodes = new HashSet<Integer>(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);

        Thread t1 = getThreadObject(hashCodes, countDownLatch);
        Thread t2 = getThreadObject(hashCodes, countDownLatch);
        Thread t3 = getThreadObject(hashCodes, countDownLatch);

        t1.start();
        t2.start();
        t3.start();

        countDownLatch.await();

        assertEquals(THREAD_COUNT, hashCodes.size());
    }

    @Test
    public void givenThreadScopedBean_WhenThreadAsksForBeanInstancesMultipleTimes_ThenSpringShouldProvideSingleton() {

        Set<Integer> hashCodes = new HashSet<Integer>();
        hashCodes.add(context.getBean(SampleBean.class).hashCode());
        hashCodes.add(context.getBean(SampleBean.class).hashCode());

        assertEquals(1, hashCodes.size());
    }

    private Thread getThreadObject(final Set<Integer> hashCodes, final CountDownLatch countDownLatch) {
        return new Thread() {

            @Override
            public void run() {
                hashCodes.add(context.getBean(SampleBean.class).hashCode());
                countDownLatch.countDown();
            }

        };
    }

}
