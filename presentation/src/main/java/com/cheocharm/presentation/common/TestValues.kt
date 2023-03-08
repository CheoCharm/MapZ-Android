package com.cheocharm.presentation.common

import com.cheocharm.domain.model.Group
import com.cheocharm.domain.model.GroupMember
import com.cheocharm.presentation.model.Sticker

object TestValues {
    val testGroups = listOf(
        Group(
            "그룹제목 1",
            "맵지 고등학교 추억 교환일기!",
            "2022.02.23",
            listOf(
                GroupMember(),
                GroupMember(),
                GroupMember(),
                GroupMember(),
                GroupMember(),
                GroupMember(),
                GroupMember(),
                GroupMember()
            ), 4
        ),
        Group(
            "그룹제목 2", "맵지 고등학교 추억 교환일기!",
            "2022.02.23",
            listOf(
                GroupMember(), GroupMember(), GroupMember()
            ), 0
        ),
        Group(
            "그룹제목 3", "맵지 고등학교 추억 교환일기!",
            "2022.02.23",
            listOf(
                GroupMember(), GroupMember(), GroupMember()
            ), 0
        ),
    )

    val testImages = listOf(
        "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Emoji/IMG_6148.JPG",
        "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary//5eda379c-a8be-4c41-bfbf-a1e984c457ff",
        "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/afd156ea-6257-4c30-9d06-f8b1ebf37609",
        "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/f4424c08-dfd3-461b-b9e3-3cf09f984f8a",
        "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/fe1189fb-c83d-4792-9c45-6e3bfbd77cda",
        "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/f3284be0-c6d9-43be-8399-e6ebd30603ae"
    )

    // TODO: 서버에 스티커 파일 업로드 후 URL 변경
    val testStickers = listOf(
        Sticker(
            "IMG_6148.JPG",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Emoji/IMG_6148.JPG"
        ),
//        Sticker(
//            "5eda379c-a8be-4c41-bfbf-a1e984c457ff",
//            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary//5eda379c-a8be-4c41-bfbf-a1e984c457ff"
//        ),
        Sticker(
            "afd156ea-6257-4c30-9d06-f8b1ebf37609",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/afd156ea-6257-4c30-9d06-f8b1ebf37609"
        ),
        Sticker(
            "f4424c08-dfd3-461b-b9e3-3cf09f984f8a",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/1/f4424c08-dfd3-461b-b9e3-3cf09f984f8a"
        ),
//        Sticker(
//            "fe1189fb-c83d-4792-9c45-6e3bfbd77cda",
//            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/fe1189fb-c83d-4792-9c45-6e3bfbd77cda"
//        ),
        Sticker(
            "f3284be0-c6d9-43be-8399-e6ebd30603ae",
            "https://mapz-bucket.s3.ap-northeast-2.amazonaws.com/Mapz/Diary/f3284be0-c6d9-43be-8399-e6ebd30603ae"
        )
    )
}
