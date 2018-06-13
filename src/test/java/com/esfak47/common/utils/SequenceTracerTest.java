package com.esfak47.common.utils;

import org.junit.Test;

/**
 * @author tony
 * @date 2018/6/13
 */
public class SequenceTracerTest {

    @Test
    public void test() {
        SequenceTracer.trace("start");
        SequenceTracer.traceStop();
        Home home = new Home();
        Company company = new Company();
        SequenceTracer.trace("call wake up");
        home.wakeUp();
        SequenceTracer.traceStop();
        SequenceTracer.trace("work");
        company.work();
        SequenceTracer.traceStop();
        SequenceTracer.trace("eat");
        company.eat();
        SequenceTracer.traceStop();
        SequenceTracer.trace("return to work");
        company.work();
        SequenceTracer.traceStop();
        SequenceTracer.trace("go to sleep");
        home.goToBed();
        SequenceTracer.traceStop();
        final String s = SequenceTracer.printAsPlantuml();
        System.out.println(s);

    }

    private static final class Home {
        public void wakeUp() {
            SequenceTracer.traceStop();
            SequenceTracer.trace("call cleanMyself");
            cleanMyself();
            SequenceTracer.traceStop();
        }

        public void cleanMyself() {
            SequenceTracer.traceStop();

            SequenceTracer.trace("finish cleaning");
        }

        public void goToBed() {
            SequenceTracer.traceStop();
            SequenceTracer.trace("sleeping");
        }
    }

    private static final class Company {
        public void work() {
            SequenceTracer.traceStop();
            SequenceTracer.trace("start working");
        }

        public void eat() {
            SequenceTracer.traceStop();
            SequenceTracer.trace("eat done");
        }
    }

}


