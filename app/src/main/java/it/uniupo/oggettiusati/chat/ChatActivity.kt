package it.uniupo.oggettiusati.chat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import it.uniupo.oggettiusati.R
import it.uniupo.oggettiusati.adapter.MessageAdapter

/**
 * Activity per la chat
 *
 * @author Amato Luca
 */
class ChatActivity: AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance().reference

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Messaggio>
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messaggioBox: EditText
    private lateinit var sendButton: ImageView

    private var receiverRoom: String? = null
    private var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_layout)


        val nome = intent.getStringExtra("nome")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = auth.currentUser?.uid


        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = nome

        chatRecyclerView = findViewById(R.id.layoutChat)
        messaggioBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        //logica per aggiungere i dati alla recycle
        this.database.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for(postSnapshot in snapshot.children){

                        val message = postSnapshot.getValue(Messaggio::class.java)
                        messageList.add(message!!)

                    }

                    messageAdapter.notifyDataSetChanged()
//                    messageAdapter.notifyItemChanged(0)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        //aggiungo il messaggio al Db
        sendButton.setOnClickListener{

            val message = messaggioBox.text.toString()
            val messageObject = Messaggio(message, senderUid)

            this.database.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    this.database.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

            messaggioBox.setText("")
        }
    }
}