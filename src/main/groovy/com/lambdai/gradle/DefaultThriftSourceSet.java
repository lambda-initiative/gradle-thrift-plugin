package com.lambdai.gradle;

import groovy.lang.Closure;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.DefaultSourceDirectorySet;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.util.ConfigureUtil;

public class DefaultThriftSourceSet implements ThriftSourceSet {
    private final SourceDirectorySet thrift;

    public DefaultThriftSourceSet(String displayName, FileResolver fileResolver) {
        thrift = new DefaultSourceDirectorySet(String.format("%s Thrift source", displayName), fileResolver);
        thrift.getFilter().include("**/*.thrift");
    }

    public SourceDirectorySet getThrift() {
        return thrift;
    }

    public ThriftSourceSet thrift(Closure configureClosure) {
        ConfigureUtil.configure(configureClosure, getThrift());
        return this;
    }
}