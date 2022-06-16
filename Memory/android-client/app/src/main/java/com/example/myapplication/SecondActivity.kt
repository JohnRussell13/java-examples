package com.example.myapplication

import android.os.Bundle
import android.os.StrictMode
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import kotlin.random.Random

class SecondActivity : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    private var smg: ServerMessagesGame? = null
    private var myTurnFlag = false
    private var score1 = 0
    private var score2 = 0

    fun handleServer(line: String) {
        runOnUiThread {
            if(line.startsWith("F: ")){
                SocketHandler.setActivityFlag(true)
                finish()
            }
            else if(line.startsWith("P: ") && !myTurnFlag){
                val lineFiltered = line.drop(3) // drop "ID: "
                val pos = lineFiltered.toInt()

                updateModels(pos)
                // Update the UI for the game
                updateViews()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        SocketHandler.setActivityFlag(false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val actionBar = supportActionBar
        actionBar!!.title = "Game"
        actionBar.setDisplayHomeAsUpEnabled(false)

        var temp = SocketHandler.getMyName() + ": $score1"
        findViewById<TextView>(R.id.player1).text = temp
        temp = SocketHandler.getOpName() + ": $score2"
        findViewById<TextView>(R.id.player2).text = temp

        smg = ServerMessagesGame(this)
        val thr = Thread(smg)
        thr.start()

        myTurnFlag = SocketHandler.getMyID() == SocketHandler.getStartID()

        findViewById<Button>(R.id.backButton).setOnClickListener {
            SocketHandler.getPrintWriter().println("F: ")
            SocketHandler.setActivityFlag(true)
            finish()
        }

        val images = mutableListOf(R.drawable.ic_card_00, R.drawable.ic_card_01, R.drawable.ic_card_02,
            R.drawable.ic_card_03, R.drawable.ic_card_04, R.drawable.ic_card_05, R.drawable.ic_card_06,
            R.drawable.ic_card_07, R.drawable.ic_card_08, R.drawable.ic_card_09, R.drawable.ic_card_10,
            R.drawable.ic_card_11, R.drawable.ic_card_12, R.drawable.ic_card_13, R.drawable.ic_card_14)
        // Add each image twice so we can create pairs
        images.addAll(images)

        // Randomize the order of images
        val rand = Random(SocketHandler.getSeed())
        images.shuffle(rand)

        buttons = listOf(imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6,
            imageButton7, imageButton8, imageButton9, imageButton10, imageButton11, imageButton12,
            imageButton13, imageButton14, imageButton15, imageButton16, imageButton17, imageButton18,
            imageButton19, imageButton20, imageButton21, imageButton22, imageButton23, imageButton24,
            imageButton25, imageButton26, imageButton27, imageButton28, imageButton29, imageButton30)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if(myTurnFlag){
                    SocketHandler.getPrintWriter().println("P: $index")
                    // Update models
                    updateModels(index)
                    // Update the UI for the game
                    updateViews()
                }
            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.ic_swap)
        }

        var temp = SocketHandler.getMyName() + ": $score1"
        findViewById<TextView>(R.id.player1).text = temp
        temp = SocketHandler.getOpName() + ": $score2"
        findViewById<TextView>(R.id.player2).text = temp
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error checking:
        if (card.isFaceUp) {
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        // Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        indexOfSingleSelectedCard = if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCards()
            position
        } else {
            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            myTurnFlag = !myTurnFlag
            null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
            if(myTurnFlag) score1++
            else score2++
            myTurnFlag = !myTurnFlag
            Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
    }
}