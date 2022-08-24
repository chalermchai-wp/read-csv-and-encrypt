package com.app.tools

import com.app.model.UserProfileTemp
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
val PASSWORD = "password"
val IV = "iv"

fun main(args: Array<String>) {

    /// part input file csv
    val file = File("part to input file .csv")
    val csv = readCsv(file.inputStream())

    println(csv)

    /// part output file csv
    FileOutputStream("part to output file .csv").apply { writeCsv(csv) }

}

fun readCsv(inputStream: InputStream): List<UserProfileTemp> {
    val reader = inputStream.bufferedReader()
    val header = reader.readLine()
    return reader.lineSequence()
        .filter { it.isNotBlank() }
        .map {

            val userProfileTemp = it.split(',', ignoreCase = false)

            UserProfileTemp(
                if(userProfileTemp.size > 1) userProfileTemp[0] else "null",
                if(userProfileTemp.size > 2) EncryptionUtils().encrypt(userProfileTemp[1], PASSWORD, IV) else "null",
                if(userProfileTemp.size > 3) EncryptionUtils().encrypt(userProfileTemp[2], PASSWORD, IV) else "null",
                if(userProfileTemp.size > 4) EncryptionUtils().encrypt(userProfileTemp[3], PASSWORD, IV) else "null",
                if(userProfileTemp.size > 5) EncryptionUtils().encrypt(userProfileTemp[4], PASSWORD, IV) else "null",
                if(userProfileTemp.size > 6) userProfileTemp[5] else "null",
            )
        }.toList()
}

fun OutputStream.writeCsv(movies: List<UserProfileTemp>) {
    val writer = bufferedWriter()
    writer.write(""""USERNAME","FIRST_NAME","LAST_NAME","CID","TEL_NO","PROVINCE_CODE"""")
    writer.newLine()
    movies.forEach {
        writer.write("${it.username}," +
                "${it.firstName}," +
                "${it.lastName}," +
                "${it.cid}," +
                "${it.telNo}," +
                "${it.provinceCode}" )
        writer.newLine()
    }
    writer.flush()
}





