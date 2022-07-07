package com.zone.plugin

import com.zone.plugin.cache.FileData
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

class ZonePlugin implements Plugin<Project> {

    public static final String ZONE_DIR = "/.zone"

    @Override
    void apply(Project project) {
        project.afterEvaluate { Project pj ->
            println("=====hello this is zone plugin1 ${pj.name}=====")

            if (project.plugins.hasPlugin("com.android.application")) {
                println("has apply plugin: com.android.application ")
                pj.tasks.register("zone") {
                    doLast {
                        println("====================增量编译====================")
                        def fileStates = ZoneCache.INSTANCE.analyzeFileState()
                        fileStates.findIndexOf {
                            println("entry ${it.toString()}")
                        }
                    }
                }
            }
            /**
             * 对于class，
             * 1. 如果是java，hook编译java文件任务（compileDebugJavaWithJavac），以获取java文件路径
             * 2. 对于是kotlin文件， hook（kaptGenerateStubsDebugKotlin）任务，以获取kotlin源文件路径
             *
             * kaptGenerateStubsDebugKotlin .kt -> .java
             * compileDebugKotlin
             */
            project.gradle.taskGraph.afterTask(new Action<Task>() {
                @Override
                void execute(Task task) {
                    if (task instanceof JavaCompile) {
                        /**
                         * java编译任务
                         */
                        def PROJECT_DIR_PATH = project.projectDir.path
                        println("${task.project.name}:${task.name}==> afterTask")
                        task.inputs.files.forEach { File sourceFile ->
                            if (sourceFile.isFile() && sourceFile.name.endsWith(".java")) {
                                ZoneCache.INSTANCE.recordFileMd5(task.project.name, sourceFile)
                                /*def newSaveFilePath = sourceFile.parent.replace(PROJECT_DIR_PATH, PROJECT_DIR_PATH + ZONE_DIR)
                                try {
                                    def saveFile = new File(newSaveFilePath, sourceFile.name)
                                    FileUtils.copyFile(sourceFile, saveFile)
                                } catch (Exception e) {
                                    e.printStackTrace()
                                }*/
                            }
                        }

                        ZoneCache.INSTANCE.printCacheFileMap()
                    }

                    if (task instanceof KaptGenerateStubsTask) {
                        // .kt -> .java
//                        println("${task.project.name}:${task.name}==> afterTask")
//                        task.inputs.files.forEach{
//                            println("input file:$it.absolutePath")
//                        }
                    }

//                    if (task instanceof) {
//
//                    }
                }
            })
        }
    }
}