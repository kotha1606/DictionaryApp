package com.example.project004

data class WordResult(
    val word: String,
    val phonetic: String?,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
)

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<String>,
)
data class Phonetic(
    val text: String,
    val audio: String,
    val sourceUrl: String?,

)
data class Definition(
    val definition: String
)
