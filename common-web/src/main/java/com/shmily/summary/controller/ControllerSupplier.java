package com.shmily.summary.controller;

@FunctionalInterface
public interface ControllerSupplier<T> {

    T get(Object request) throws Exception;
}
