package com.zone.plugin.cache

class FileData {
    String filePath;
    FileStatus fileStatus
    String md5

    @Override
    String toString() {
        return "md5:$md5 fileStatus:${fileStatus.name()} filePath:$filePath"
    }
}

enum FileStatus{
    INIT,
    ADD,
    MODIFY,
    DELETE,
    NOT_CHANGE
}