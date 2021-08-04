package com.example.memeshare

import android.R.attr.data
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
       var currentimage:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load()

    }
    fun load(){
        pf.visibility=View.VISIBLE
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentimage=response.getString("url")
               Glide.with(this).load(currentimage).listener(object :RequestListener<Drawable>{
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       pf.visibility=View.GONE
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       pf.visibility=View.GONE
                       return false
                   }

               }).into(imageView2)

            },
            { error ->
                Toast.makeText(this,"FAILED",Toast.LENGTH_SHORT).show()
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun share(view: View) {
       val intents=Intent(Intent.ACTION_SEND)


                  //if no picture, just text set - this MIME
            intents.putExtra(Intent.EXTRA_TEXT,"Hey check this MEME FROM my meme APP ${currentimage}")
            intents.setType("text/plain")




       val chooser=Intent.createChooser(intents,"Great meme")

        try {
            startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            // Define what your app should do if no activity can handle the intent.
        }

    }
    fun next(view: View) {
        load()
    }
}