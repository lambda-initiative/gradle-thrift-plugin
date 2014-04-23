package com.lambdai.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import com.lambdai.gradle.tasks.thrift.ScroogeCompiler

class ThriftCompile extends DefaultTask {
    def source = project.file("src/main/thrift")
    File output = project.file("src/gen/scala")

    def opts = ["--finagle"]

    @TaskAction
    def compile() {
        ScroogeCompiler.compile(source, output, opts)
    }
}
