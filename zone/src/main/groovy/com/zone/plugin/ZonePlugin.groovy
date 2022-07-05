package com.zone.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ZonePlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.afterEvaluate { Project pj ->
            println("=====hello this is zone plugin ${pj.name}=====")
        }
    }
}