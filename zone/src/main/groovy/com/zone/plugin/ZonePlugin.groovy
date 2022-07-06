package com.zone.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project

class ZonePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate { Project pj ->
            println("=====hello this is zone plugin1 ${pj.name}=====")

            if (project.plugins.hasPlugin("com.android.application")) {
                println("has apply plugin: com.android.application ")
                pj.tasks.register("hello") {
                    doLast {
                        println("hello task do last")
                    }
                }
            }
        }
    }
}