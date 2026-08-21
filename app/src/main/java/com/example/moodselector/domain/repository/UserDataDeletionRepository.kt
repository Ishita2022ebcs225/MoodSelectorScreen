package com.example.moodselector.domain.repository

interface UserDataDeletionRepository {

    /*
     * --------------------------------------------------
     * DELETE ALL USER DATA
     * --------------------------------------------------
     *
     * Deletes all user-specific application data
     * associated with the supplied userId.
     *
     * This includes persisted Room data and
     * user-specific preferences.
     */

    suspend fun deleteAllUserData(
        userId: String
    )
}

