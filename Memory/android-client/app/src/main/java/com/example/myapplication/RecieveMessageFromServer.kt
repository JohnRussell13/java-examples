package com.example.myapplication

import java.io.BufferedReader
import java.io.IOException


class ReceiveMessageFromServer(parent: MainActivity) : Runnable {
    private var parent: MainActivity
    private var br: BufferedReader

    override fun run() {
        while (true) {
            var line: String?
            try {
                if(SocketHandler.getActivityFlag()){ // check activity
                    line = br.readLine()
                    parent.setText(line)
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
