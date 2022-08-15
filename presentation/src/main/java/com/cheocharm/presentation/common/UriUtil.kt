package com.cheocharm.presentation.common

import android.content.Context
import android.net.Uri
import java.io.File

object UriUtil {
    fun getFileFromUri(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)

        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.copyToFile(context, uri, file)
        return File(file.absolutePath)
    }

    fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)?.split("/")?.last() ?: return ""

        return "$name.$ext"
    }
}
