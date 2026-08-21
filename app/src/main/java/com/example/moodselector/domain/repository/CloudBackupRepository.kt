package com.example.moodselector.domain.repository

/**
 * Handles backup, restoration, and deletion of the authenticated user's
 * local Room data using Firebase Firestore.
 *
 * Room remains the app's local/offline data source.
 * Firestore is used as a cloud backup.
 */
interface CloudBackupRepository {

    /*
     * --------------------------------------------------
     * BACKUP
     * --------------------------------------------------
     *
     * Uploads the authenticated user's local data
     * to Firestore.
     *
     * The userId must be the Firebase Authentication UID.
     */
    suspend fun backupUserData(
        userId: String
    ): Result<Unit>


    /*
     * --------------------------------------------------
     * RESTORE
     * --------------------------------------------------
     *
     * Restores the authenticated user's cloud data
     * into the local Room database.
     *
     * The userId must be the Firebase Authentication UID.
     */
    suspend fun restoreUserData(
        userId: String
    ): Result<Unit>


    /*
     * --------------------------------------------------
     * SYNC
     * --------------------------------------------------
     *
     * Performs the cloud synchronization process for
     * the authenticated user.
     *
     * The implementation will determine the appropriate
     * backup/restore sequence.
     */
    suspend fun syncUserData(
        userId: String
    ): Result<Unit>


    /*
     * --------------------------------------------------
     * DELETE CLOUD USER DATA
     * --------------------------------------------------
     *
     * Deletes all Firestore cloud backup data belonging
     * to the authenticated user.
     *
     * The userId must be the Firebase Authentication UID.
     */
    suspend fun deleteUserData(
        userId: String
    ): Result<Unit>
}

