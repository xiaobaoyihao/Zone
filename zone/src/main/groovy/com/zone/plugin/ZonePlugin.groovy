package com.zone.plugin

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile

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
            /**
             * 对于class，
             * 1. 如果是java，hook编译java文件任务（compileBetaDebugJavaWithJavac），以获取java文件路径
             * 2. 对于是kotlin文件， hook（kaptGenerateStubsBetaDebugKotlin）任务，以获取kotlin源文件路径
             */
            project.gradle.taskGraph.afterTask(new Action<Task>() {
                @Override
                void execute(Task task) {
                    if(task instanceof JavaCompile){
                        /**
                         * java编译任务
                         */
                        println("${task.project.name}:afterTask:${task.name}")
                        task.inputs.files.forEach{
                            println("input file:$it.absolutePath")
                        }
                    }
                }
            })
        }
    }
}