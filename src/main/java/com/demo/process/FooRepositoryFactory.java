package com.demo.process;

public class FooRepositoryFactory {
    private static FooRepository fooRepository = new FooRepository();

    public static FooRepository getFooRepository() {
        return fooRepository;
    }

}