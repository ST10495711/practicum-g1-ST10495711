package vcmsa.ci.exammusicpracticum

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vcmsa.ci.exammusicpracticum.ui.theme.ExamMusicPracticumTheme

class MainActivity : ComponentActivity() {
    private val artistsList =  ArrayList<String>()
    private val songList =  ArrayList<String>()
    private val ratingList = ArrayList<Int>()
    private val commentList =  ArrayList<String>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        // Layout references
        val addLayout = findViewById<LinearLayout>(R.id.layoutAdd)
        val viewLayout = findViewById<LinearLayout>(R.id.layoutView)

        // Add Screen buttons
        val btnAddSong = findViewById<Button>(R.id.btnAddSong)
        val btnGoToView = findViewById<Button>(R.id.btnGoToView)
        val btnExit = findViewById<Button>(R.id.btnExit)

        // View Screen buttons
        val btnShowAll = findViewById<Button>(R.id.btnShowAll)
        val btnShowRate = findViewById<Button>(R.id.btnShowRating)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val txtResult = findViewById<TextView>(R.id.textResult)

        // Navigation logic
        btnGoToView.setOnClickListener{
            addLayout.visibility = View.GONE
            viewLayout.visibility = View.VISIBLE
        }
        btnBack.setOnClickListener{
            viewLayout.visibility = View.GONE
            addLayout.visibility = View.VISIBLE
        }
        btnExit.setOnClickListener{
            finish()
        }

        // Add screen
        btnAddSong.setOnClickListener{
            val dialogView = layoutInflater.inflate(R.layout.second_screen, null)
            val edtArtist = dialogView.findViewById<EditText>(R.id.edtArtist)
            val edtSong = dialogView.findViewById<EditText>(R.id.edtSong)
            val edtRating = dialogView.findViewById<EditText>(R.id.edtRating)
            val edtComment = dialogView.findViewById<EditText>(R.id.edtComment)

            AlertDialog.Builder(this)
                .setTitle("Add song")
                .setView(dialogView)
                .setPositiveButton("Add") { _, _ ->

                    val artist = edtArtist.text.toString()
                    val song = edtSong.text.toString()
                    val rating = edtRating.text.toString()
                    val comment = edtComment.text.toString()


                    if (artist.isBlank() || song.isBlank()|| rating == null || comment.isBlank()) {
                        showError("All fields must be correct filled.")
                        Log.e("ERROR", "Invalid input:$artist, $song, $rating, $comment")
                        return@setPositiveButton
                    }
                    artistsList.add(artist)
                    songList.add(song)
                    ratingList.add(3)
                    commentList.add(comment)

                    Log.i("ITEM ADDED", "$artist added with qty $rating")
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
        // Display all artist
        btnShowAll.setOnClickListener{
            val result = StringBuilder()
            for (i in artistsList.indices){
                result.append("Artist: ${artistsList[i]}\nSong: ${songList[i]}\nRating: ${ratingList[i]}\nComment: ${commentList[i]}\n\n")
            }
            txtResult.text = result.toString()
            Log.i("Display", "All artists displayed")
        }
                   // Display artist with qty  >= 2
        btnShowRate.setOnClickListener{
            val result = StringBuilder()
            for (i in ratingList.indices){
                if (ratingList[i]  >= 3){
                    result.append("Artist:${artistsList[i]} - Qty ${ratingList[i]}\n")
                }
            }
            txtResult.text = result.toString()
            Log.i("Display", "Filtered artist displayed")
        }
    }
    private fun showError(message: String){
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()

    }
}