package com.lambdai.gradle

import com.lambdai.gradle.tasks.*
import com.lambdai.gradle.tasks.ThriftScalaCompile
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileTreeElement
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.SourceSet

class ThriftPlugin implements Plugin<Project> {

    public static final String CLEAN_THRIFT_TASK = 'cleanThrift'
    private Project project
    def languages = ["scala"]
    def genDir = "src/gen/"

    @Override
    void apply(final Project project) { // Currently only deal with scala
        this.project = project

        languages.each { String lang ->
            project.apply plugin: lang
        }

        project.task(CLEAN_THRIFT_TASK, type: ThriftClean) {
            group = 'Thrift'
            description = 'Delete the output directories for each compiled language.'
        }
        project.tasks[BasePlugin.CLEAN_TASK_NAME].dependsOn(CLEAN_THRIFT_TASK)

        configureSourceSetDefaults()
    }

    private void configureSourceSetDefaults() {
        project.sourceSets.all { SourceSet sourceSet ->
            sourceSet.convention.plugins.thrift = new DefaultThriftSourceSet(sourceSet.displayName, project.getFileResolver())
            sourceSet.thrift.srcDir { project.file("src/$sourceSet.name/thrift") }

            // TODO add scala source dir
            sourceSet.allSource.source(sourceSet.thrift)
            sourceSet.resources.filter.exclude { FileTreeElement element -> sourceSet.thrift.contains(element.file) }

            configureThriftCompile(sourceSet)
        }
    }

    def compileTaskClass = [
        scala: ThriftScalaCompile,
        java: ThriftJavaCompiler
    ]

    def thriftTask = [
        scala: "scrooge",
        java: "thriftJava"
    ]

    private void configureThriftCompile(SourceSet sourceSet) {
        languages.each { String lang ->
            def taskName = sourceSet.getCompileTaskName(thriftTask[lang])
            ThriftCompile thriftCompile = project.tasks.create(taskName, compileTaskClass[lang])
            thriftCompile.description = "Compiles the $sourceSet.thrift."
            thriftCompile.source = sourceSet.thrift
            thriftCompile.output = project.file(genDir + lang)
            sourceSet.scala.srcDir { thriftCompile.output }

            project.tasks[sourceSet.getCompileTaskName(lang)].dependsOn(taskName)
        }
    }
}
