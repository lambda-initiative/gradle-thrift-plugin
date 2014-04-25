package com.lambdai.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory

abstract class ThriftCompile extends DefaultTask {
    @InputFiles
    def source

    @OutputDirectory
    File output

    abstract def compile();
}
