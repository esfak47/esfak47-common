package com.esfak47.common.utils;

import org.junit.Test;

/**
 * @author tony
 * @date 2018/6/13
 */
public class SequenceTracerTest {

    @Test
    public void test() {
        final SequenceTracer sequenceTracer = SequenceTracer.SequenceTracerFactory.getSequenceTracer();

        sequenceTracer.selfCall("start");
        sequenceTracer.selfCallSuccess();
        Home home = new Home();
        Company company = new Company();
        sequenceTracer.newCallRequest("call wake up");
        home.wakeUp();
        sequenceTracer.callSuccess();
        sequenceTracer.newCallRequest("work");
        company.work();
        sequenceTracer.callSuccess();
        sequenceTracer.newCallRequest("eat");
        company.eat();
        sequenceTracer.callSuccess();
        sequenceTracer.newCallRequest("return to work");
        company.work();
        sequenceTracer.callSuccess();
        sequenceTracer.newCallRequest("go to sleep");
        home.goToBed();
        sequenceTracer.callSuccess();
        sequenceTracer.newCallRequest("go to sleep");
        home.goToBed();
        sequenceTracer.callFailed("already sleeping");

        final String s = sequenceTracer.printAsPlantuml();
        System.out.println(s);

    }

    private static final class Home {

        private boolean sleeping = false;

        public void wakeUp() {
            final SequenceTracer sequenceTracer = SequenceTracer.SequenceTracerFactory.getSequenceTracer();
            sequenceTracer.acceptSuccess();
            sequenceTracer.newCallRequest("call cleanMyself");
            cleanMyself();
            sequenceTracer.callSuccess();
            sequenceTracer.returnSuccess("waked up");
        }

        public void cleanMyself() {

            final SequenceTracer sequenceTracer = SequenceTracer.SequenceTracerFactory.getSequenceTracer();
            sequenceTracer.acceptSuccess();

            sequenceTracer.returnSuccess("clean finished");
        }

        public void goToBed() {

            final SequenceTracer sequenceTracer = SequenceTracer.SequenceTracerFactory.getSequenceTracer();
            if (this.sleeping) {
                sequenceTracer.acceptFailed("already sleeping");

            } else {
                sequenceTracer.acceptSuccess();
                this.sleeping = true;
                sequenceTracer.returnSuccess("sleeping");
            }

        }
    }

    private static final class Company {
        final static SequenceTracer sequenceTracer = SequenceTracer.SequenceTracerFactory.getSequenceTracer();

        public void work() {
            sequenceTracer.acceptSuccess();
            sequenceTracer.returnSuccess("start working");
        }

        public void eat() {
            sequenceTracer.acceptSuccess();
            sequenceTracer.returnSuccess("eat done");
        }
    }

}


