package com.lambdai.gradle.tasks

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

class ThriftClean extends DefaultTask {
    @TaskAction
    def cleanThrfit() {
        project.file("src/gen/scala").deleteDir()
    }
}
