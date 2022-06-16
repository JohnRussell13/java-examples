package com.example.myapplication

import java.io.BufferedReader
import java.io.IOException


class ServerMessagesGame(parent: SecondActivity) : Runnable {
    private var parent: SecondActivity
    private var br: BufferedReader

    override fun run() {
        while (true) {
            var line: String?
            try {
                if(!SocketHandler.getActivityFlag()){ // check activity
                    line = br.readLine()
                    parent.handleServer(line)
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }
    }

    init {
        this.parent = parent
        br = SocketHandler.getBufferedReader()
    }
}
