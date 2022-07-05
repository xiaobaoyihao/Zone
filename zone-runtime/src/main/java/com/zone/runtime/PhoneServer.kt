package com.zone.runtime

import android.content.ServiceConnection
import android.net.LocalServerSocket
import android.net.LocalSocket
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import kotlin.concurrent.thread

object PhoneServer {

    var serverSocket: LocalServerSocket? = null

    fun startServer(name: String) {

        try {// free resources
            serverSocket?.accept()?.close()

            // new a serverSocket
            serverSocket = LocalServerSocket(name)

        } catch (e: Exception) {
            Log.e(TAG_ZONE, "start server error", e)
        }
    }

    fun startListener() {
        thread(start = true) {
            /**
             * listening pc msg
             */

            while (true) {
                val phoneServerSocket = serverSocket ?: break
                val socket = phoneServerSocket.accept()
                ServerReplayTask(socket).run()
                Log.d(
                    TAG_ZONE,
                    "Received connection from IDE: in thread:${Thread.currentThread().name}"
                )
            }
        }
    }

    class ServerReplayTask(private val socket: LocalSocket) : Runnable {

        override fun run() {
            Log.d(TAG_ZONE, "begin to accept msg from pc")
            val input = DataInputStream(socket.inputStream)
            val output = DataOutputStream(socket.outputStream)

            try {
                handle(input, output)
            } catch (e: Exception) {
                Log.i(TAG_ZONE, "handle message fails", e)
            } finally {
                input.close()
                output.close()
            }
        }

        private fun handle(input: DataInputStream, output: DataOutputStream) {
            val protocolMagic = input.readLong()
            if (protocolMagic != PROTOCOL_ID) {
                Log.w(TAG_ZONE, "server receive a unknow msg: protocolMagic:${protocolMagic}")
            }
        }
    }
}