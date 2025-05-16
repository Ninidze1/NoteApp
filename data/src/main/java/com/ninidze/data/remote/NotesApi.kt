package com.ninidze.data.remote

import com.ninidze.data.dto.CreateNoteDto
import com.ninidze.data.dto.DeleteNoteDto
import com.ninidze.data.dto.NoteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * Remote data source for interacting with the Notes backend.
 *
 * The backend exposes the following endpoints (query–param driven):
 * ``
 * GET    /?path=list                       – fetch all notes
 * POST   /?path=create                     – create a new note (request body required)
 * PUT    /?path=update&id={noteId}         – update an existing note (request body required)
 * DELETE /?path=delete&id={noteId}         – delete a note by its id
 * ```
 */
interface NotesApi {

    /** Returns every note stored on the server. */
    @GET("./")
    suspend fun getNotes(
        @Query("path") path: String = "list"
    ): List<NoteDto>

    /** Creates a new note on the server and returns the created entity. */
    @POST("./")
    suspend fun createNote(
        @Query("path") path: String = "create",
        @Body request: CreateNoteDto
    ): NoteDto

    /** Updates an existing note and returns the updated entity. */
    @PUT("./")
    suspend fun updateNote(
        @Query("path") path: String = "update",
        @Query("id") id: String,
        @Body request: NoteDto
    ): NoteDto

    /** Deletes a note identified by [id] and returns a confirmation wrapper. */
    @DELETE("./")
    suspend fun deleteNote(
        @Query("path") path: String = "delete",
        @Query("id") id: String
    ): DeleteNoteDto
}