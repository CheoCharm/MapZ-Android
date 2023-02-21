package com.cheocharm.domain.model

data class WriteImageResponse(val statusCode: Int, val customCode: String, val data: Data) {
    inner class Data(val diary: Long, val imageURLs: List<String>, message: String)
}
