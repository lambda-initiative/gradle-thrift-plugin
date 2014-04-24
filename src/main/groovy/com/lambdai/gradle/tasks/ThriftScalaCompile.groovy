package com.lambdai.gradle.tasks

import com.lambdai.gradle.tasks.ThriftCompile
import com.lambdai.gradle.tasks.thrift.ScroogeCompiler
import org.gradle.api.tasks.TaskAction

class ThriftScalaCompile extends ThriftCompile {
    def opts = ["--finagle"]

    @TaskAction
    def compile() {
        ScroogeCompiler.compile(source, output, opts)
    }
}