/*
 * © Copyright - Astride UG (haftungsbeschränkt) 2018 - 2019.
 */

package de.astride.planetsystem.core.utils

import com.boydti.fawe.`object`.schematic.Schematic
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.*


/**
 * Created on 27.08.2019 11:27.
 * @author Lars Artmann | LartyHD
 */

private const val bucket: String = "cosmicsky-planet-schmatics-high-access"
private const val suffix: String = ".schematic"
private val storage: Storage = StorageOptions.getDefaultInstance().service

fun Schematic.save(uuid: UUID) {

    val blobInfo = BlobInfo.newBuilder(uuid.getBlobId()).setContentType("application/octet-stream").build()

    val schematicBytes = ByteArrayOutputStream().run {
        save(this, ClipboardFormat.SCHEMATIC)
        toByteArray()
    }

    if (schematicBytes.isEmpty()) return

    val blob = storage.create(blobInfo)
    blob.writer().use {
        val byteBuffer = ByteBuffer.wrap(schematicBytes)
        it.write(byteBuffer)
    }

}

fun UUID.getSchematic(): Schematic {

    val blob = storage.get(getBlobId()) ?: storage.get("default".getBlobId())
    val bytes = blob.reader().use {
        ByteBuffer.allocate(blob.size.toInt()).apply { it.read(this) }
    }.array()

    val inputStream = ByteArrayInputStream(bytes)
    val clipboard = ClipboardFormat.SCHEMATIC.getReader(inputStream).read(null)
    return Schematic(clipboard)

}

private fun Any.getBlobId(): BlobId = BlobId.of(bucket, "$this$suffix")
