package com.lambdai.gradle

import com.lambdai.gradle.tasks.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileTreeElement
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.SourceSet

class ThriftPlugin implements Plugin<Project> {

    public static final String CLEAN_THRIFT_TASK = 'cleanThrift'
    private Project project

    @Override
    void apply(final Project project) { // Currently only deal with scala
        this.project = project

        // TODO support multiple language
        project.apply plugin: 'scala'

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
    private void configureThriftCompile(SourceSet sourceSet) {
        def taskName = sourceSet.getCompileTaskName('thrift')
        ThriftCompile thriftCompile = project.tasks.create(taskName, ThriftCompile)
        thriftCompile.description = "Compiles the $sourceSet.thrift."
        thriftCompile.source = sourceSet.thrift
//        thriftCompile.output = project.file("src/gen/scala")

        // TODO for different language, set different dependency
        project.tasks[sourceSet.getCompileTaskName('scala')].dependsOn(taskName)
    }
}
