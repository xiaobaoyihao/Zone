package com.zone.runtime

import android.app.ActivityManager
import android.content.Context
import android.os.Process

object ZoneRuntimeUtils {
    var applicationId: String? = null

    /**
     * app启动是需要初始化，注入applicationId
     */
    fun init(applicationId: String) {
        this.applicationId = applicationId
    }

    fun isRunInMainProcess(context: Context): Boolean {
        applicationId ?: return false

        var isMainProcess = false
        val pid = Process.myPid()
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            if (applicationId.equals(processInfo.processName)) {
                if (processInfo.pid == pid) {
                    isMainProcess = true
                    break
                }
            }
        }
        return isMainProcess
    }
}