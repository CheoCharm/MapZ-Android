package com.cheocharm.presentation.util

import android.content.Context
import android.util.Log
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.cheocharm.presentation.BuildConfig
import java.io.File

object AWSUtil {
    fun uploadWithTransferUtility(context: Context, fileName: String, file: File) {
        val awsCredentials: AWSCredentials =
            BasicAWSCredentials(BuildConfig.BUCKET_ACCESS_KEY, BuildConfig.BUCKET_SECRET_KEY)
        val s3Client = AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2))

        val transferUtility: TransferUtility =
            TransferUtility.builder().s3Client(s3Client).context(context).build()
        TransferNetworkLossHandler.getInstance(context)

        val uploadObserver: TransferObserver =
            transferUtility.upload("mapz-bucket/Mapz/Diary", fileName, file)

        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState?) {
                if (state == TransferState.COMPLETED) {
                    Log.d(javaClass.name, "[ID: $id] Transfer complete")
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val done: Int = ((bytesCurrent.toDouble() / bytesTotal) * 100.0).toInt()
                Log.d(javaClass.name, "[ID: $id] $done% percent done")
            }

            override fun onError(id: Int, ex: Exception?) {
                Log.e(javaClass.name, "[ID: $id] $ex")
            }
        })
    }
}
