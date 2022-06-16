package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    private var id = -1
    private var rmfs: ReceiveMessageFromServer? = null
    private var submitFlag = true
    private var totalClicked = -1

    fun setText(line: String) {
        runOnUiThread {
            if(line.startsWith("ID: ")){
                val lineFiltered = line.drop(4) // drop "ID: "
                id = lineFiltered.toInt()
            }
            else if (line.startsWith("Users: ")){
                val lineFiltered = line.drop(7) // drop "Users: "
                val lineParts = lineFiltered.split(" ")

                findViewById<LinearLayout>(R.id.usersLayout)?.removeAllViews()

                for ((count, elem) in lineParts.withIndex()){
                    if(count == id) continue
                    val textView = TextView(this)
                    textView.id = count
                    textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    textView.text = elem
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24F)
                    findViewById<LinearLayout>(R.id.usersLayout)?.addView(textView)
                    textView.setOnClickListener {
                        if (totalClicked == -1) {
                            textView.setTextColor(Color.rgb(0,0,200))
                            SocketHandler.getPrintWriter().println("Q: $count 1")
                            totalClicked = count
                        } else if (totalClicked == count) {
                            SocketHandler.getPrintWriter().println("Q: $count 0")
                            textView.setTextColor(Color.rgb(128,128,128))
                            totalClicked = -1
                        }
                    }
                }
            }
            else if(line.startsWith("Locked: ")){
                val lineFiltered = line.drop(8) // drop "Locked: "
                val parts = lineFiltered.split(" ")
                for(part in parts){
                    if(part.toInt() == id) break
                    val textView = findViewById<TextView>(part.toInt())
                    textView.setTextColor(Color.rgb(200, 200, 200))
                    textView.setOnClickListener {  }
                }
            }

            else if (line.startsWith("Q: ")){
                val lineFiltered = line.drop(3) // drop "Q: "
                val parts = lineFiltered.split(" ")
                val user = parts[0].toInt()
                val userFlag = parts[1] == "1"

                val textView = findViewById<TextView>(user)
                if(userFlag){
                    textView.setTextColor(Color.rgb(255,128,0))
                    textView.setOnClickListener {
                        if (totalClicked == -1) {
                            SocketHandler.getPrintWriter().println("A: $user 1")
                            textView.setTextColor(Color.rgb(128,128,128))
                            totalClicked = user // for "S: " error
                        }
                    }
                }
                else{
                    textView.setTextColor(Color.rgb(128,128,128))
                    textView.setOnClickListener {
                        if (totalClicked == -1) {
                            textView.setTextColor(Color.rgb(0,0,200))
                            totalClicked = user
                        } else if (totalClicked == user) {
                            textView.setTextColor(Color.rgb(128,128,128))
                            totalClicked = -1
                        }}
                }
            }
            else if(line.startsWith("S: ")){
                val lineFiltered = line.drop(3) // drop "S: "
                val parts = lineFiltered.split(" ")
                SocketHandler.setSeed(parts[0].toInt())
                SocketHandler.setMyID(id)
                SocketHandler.setOpID(totalClicked)
                SocketHandler.setStartID(parts[1].toInt())
                SocketHandler.setMyName((findViewById<EditText>(R.id.usernameEditText)).text.toString())
                SocketHandler.setOpName(parts[2])

                findViewById<TextView>(totalClicked).setTextColor(Color.rgb(128,128,128))
                totalClicked = -1

                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Main Menu"

        SocketHandler.setActivityFlag(true)

        // 54.152.119.193:6001
        val ip: EditText = findViewById(R.id.ipEditText)
        val port: EditText = findViewById(R.id.portEditText)
        val userName: EditText = findViewById(R.id.usernameEditText)

//        val intent = Intent(this, SecondActivity::class.java)
//        startActivity(intent)
        findViewById<Button>(R.id.submitButton).setOnClickListener {
            if(submitFlag){
                Executors.newSingleThreadExecutor().execute {
                    try {
                        SocketHandler.setSocket(Socket(ip.text.toString(), port.text.toString().toInt()))
                        SocketHandler.setPrintWriter(PrintWriter(SocketHandler.getSocket()!!.getOutputStream(), true))
                        SocketHandler.setBufferedReader(BufferedReader(InputStreamReader(SocketHandler.getSocket()!!.getInputStream())))
                        rmfs = ReceiveMessageFromServer(this)
                        val thr = Thread(rmfs)
                        thr.start()

                        submitFlag = false // should be exactly here

                        runOnUiThread{
                            ip.setTextColor(Color.rgb(192,192,192))
                            port.setTextColor(Color.rgb(192,192,192))
                            userName.setTextColor(Color.rgb(192,192,192))

                            ip.isFocusable = false
                            port.isFocusable = false
                            userName.isFocusable = false
                        }

                        SocketHandler.getPrintWriter().println(userName.text.toString())


//                    to disconnect run:
//                      socket.close()
//                    or turn off the app
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}


