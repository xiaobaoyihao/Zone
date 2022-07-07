package com.zone.plugin

import com.zone.plugin.cache.FileData
import com.zone.plugin.cache.FileStatus
import com.zone.plugin.utils.Md5Utils

import java.io.File

class ZoneCache {

    private def cacheFileMap = new HashMap<String,ArrayList<FileData>>()

    private def moduleClassRootMap = new HashMap<String, String>()

    public static final ZoneCache INSTANCE = new ZoneCache()

    private ZoneCache(){}

    /**
     * 记录原始文件的md5值
     */
    def recordFileMd5(String projectName, File file) {
        def arrayList = cacheFileMap.get(projectName)
        if (arrayList == null) {
            arrayList = new ArrayList<FileData>()
            cacheFileMap.put(projectName, arrayList)
        }
        println("file path:${file.absolutePath}")

        def item = new FileData()
        item.filePath = file.absolutePath
        item.fileStatus = FileStatus.INIT
        item.md5 = Md5Utils.generateMD5(file)

        arrayList.add(item)
    }

    def printCacheFileMap(){
        println("====================printCacheFileMap====================")
        cacheFileMap.entrySet().findIndexOf {
            println("project name:${it.key}")
            println("file list:\n")
            it.value.findIndexOf {
                println("file data:$it")
            }
        }
    }

    Map<String, ArrayList<FileData>> analyzeFileState(){
        def modifyFilesMap = new HashMap<String, ArrayList<FileData>>()

        cacheFileMap.entrySet().findIndexOf { entry ->
            def pName = entry.key
            def fileList = entry.value

            def addFile = new HashSet<String>()

            fileList.forEach { fileData ->

                println("fileData:${fileData}")

                def filePath = fileData.filePath
                def currentFile = new File(filePath)
                if (!currentFile.exists()) {
                    // the file is be deleted
                    def newFileData = new FileData()
                    newFileData.filePath = fileData.filePath
                    newFileData.fileStatus = FileStatus.DELETE
                    newFileData.md5 = ""

                    def list = modifyFilesMap.get(pName)
                    if (list == null) {
                        list = new ArrayList<FileData>()
                        modifyFilesMap.put(pName, list)
                    }
                    list.add(newFileData)
                } else {
                    def newMd5 = Md5Utils.generateMD5(currentFile)

                    if (fileData.md5 != newMd5) {
                        // modify
                        def newFileData = new FileData()
                        newFileData.filePath= fileData.filePath
                        newFileData.fileStatus = FileStatus.MODIFY
                        newFileData.md5 = newMd5

                        def list = modifyFilesMap.get(pName)
                        if (list == null) {
                            list = new ArrayList<FileData>()
                            modifyFilesMap.put(pName, list)
                        }
                        list.add(newFileData)

                    } else if (fileData.md5 == newMd5) {
                        // not change
                        // do nothing?
                    }
                }
            }
        }


        return modifyFilesMap
    }
}