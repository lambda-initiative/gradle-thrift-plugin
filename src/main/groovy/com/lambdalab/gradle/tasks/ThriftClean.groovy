package com.lambdalab.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;

class ThriftClean extends DefaultTask {
    @TaskAction
    def cleanThrfit() {
        project.file("src/gen").deleteDir()
    }
}
