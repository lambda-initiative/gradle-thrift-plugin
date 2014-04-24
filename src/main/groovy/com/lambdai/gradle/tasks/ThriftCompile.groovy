package com.lambdai.gradle.tasks

import org.gradle.api.DefaultTask

abstract class ThriftCompile extends DefaultTask {
    def source
    File output

    abstract def compile();
}
