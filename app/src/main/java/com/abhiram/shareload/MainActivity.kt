package com.abhiram.shareload

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.abhiram.shareload.responses.InstagramResponse
import com.abhiram.shareload.retrofit.EndPoints
import com.abhiram.shareload.retrofit.RetrofitHelper
import com.abhiram.shareload.ui.theme.LightGrey40
import com.abhiram.shareload.ui.theme.PurpleGrey40
import com.abhiram.shareload.ui.theme.PurpleGrey80
import com.abhiram.shareload.ui.theme.ShareloadTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import kotlin.contracts.Returns

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intent = intent
        val action = intent.action
        val type = intent.type


        setContent {
            var status by remember{
                mutableStateOf(true)
            }
            val context = LocalContext.current
            var url = ""
            var name = ""
            var qt1 = Video("","")
            val coroutineScope = rememberCoroutineScope()
            if(action.equals("android.intent.action.SEND")&&type.equals("text/plain")){
                url = intent.getStringExtra("android.intent.extra.TEXT")!!.toString()
            }
            val extractedLink = url.substring(0,17)
            if (extractedLink.equals("https://www.insta")){
                val insta = RetrofitHelper.getInstance().create(EndPoints::class.java)
                try {
                    coroutineScope.launch {
                        val response = insta.instagram(url)
                        Log.e("Response",response.toString())
                        try {
                            if (response.success==true){
                                name = response.title.toString()
                                val res = response!!.links!![0]
                                qt1 = Video(res.quality.toString(),res.link.toString())
                                Log.e("Link",qt1.link)
                                val request = DownloadManager.Request(Uri.parse(qt1.link))
                                    .setTitle("File Download")
                                    .setDescription("Downloading video file...")
                                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "file.mp4")
                                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                                val downloadId = manager.enqueue(request)
                                Toast.makeText(context, "Download Started!", Toast.LENGTH_SHORT).show()
                                finish()
                            }else{
                                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }catch (e : Exception){
                            Log.e("Exception",e.toString())
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }catch (e : Exception){
                    Log.e("Exception",e.toString())
                    Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }else if (extractedLink.equals("https://youtube.c")){
                val yt = RetrofitHelper.getInstance().create(EndPoints::class.java)
                try{
                    coroutineScope.launch {
                        val response = yt.youtube(url)
                        Log.e("Response",response.toString())
                        try {
                            if (response.success==true){
                                name = response.title.toString()
                                val res = response!!.links!![0]
                                qt1 = Video(res?.quality.toString(),res?.link.toString())
                                Log.e("Link",qt1.link)
                                val request = DownloadManager.Request(Uri.parse(qt1.link))
                                    .setTitle("File Download")
                                    .setDescription("Downloading video file...")
                                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "file.mp4")
                                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                                val downloadId = manager.enqueue(request)
                                Toast.makeText(context, "Download Started!", Toast.LENGTH_SHORT).show()
                                finish()
                            }else{
                                Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }catch (e : Exception){
                            Log.e("Exception",e.toString())
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }catch (e : Exception){
                    Log.e("Exception",e.toString())
                    Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }else{
                Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show()
                this.finish()
            }
            Download()
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Download() {
    Box(modifier = Modifier.fillMaxSize(1F)){
        Row(
            modifier = Modifier
                .padding(start = 32.dp)
                .padding(end = 32.dp)
                .fillMaxWidth(1F)
                .background(Color.White)
                .padding(14.dp)
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .height(46.dp)
                    .width(46.dp),
                color = PurpleGrey40,
                trackColor = LightGrey40
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Loading",
                fontSize = 20.sp,
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
