package com.lambdalab.gradle;

import groovy.lang.Closure;
import org.gradle.api.file.SourceDirectorySet;

public interface ThriftSourceSet {
    /**
     * Returns the source to be compiled by the thrift compiler for this source set.
     *
     * @return The Thrift source. Never returns null.
     */
    SourceDirectorySet getThrift();

    /**
     * Configures the Thrift source for this set.
     *
     * <p>The given closure is used to configure the {@link org.gradle.api.file.SourceDirectorySet} which contains the Thrift source.
     *
     * @param configureClosure The closure to use to configure the Thrift source.
     * @return this
     */
    ThriftSourceSet thrift(Closure configureClosure);
}