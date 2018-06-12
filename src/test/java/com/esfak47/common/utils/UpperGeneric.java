package com.esfak47.common.utils;

class UpperGeneric {
    static class Generic<T, R> {
        T t;
        R r;

        protected Generic(T t, R r) {
            this.t = t;
            this.r = r;
        }

        void foo() {
            System.err.println(t + " " + r);
        }
    }
}