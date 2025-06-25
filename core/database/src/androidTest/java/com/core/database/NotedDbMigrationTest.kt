package com.core.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotedDbMigrationTest {
    private val notesDbTest = "NotesDbTest"

    @get:Rule
    val helper: MigrationTestHelper =
        MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            NotesDb::class.java,
            emptyList(),
            FrameworkSQLiteOpenHelperFactory(),
        )

    @Test
    fun migrateFrom1To2_shouldMatchSchema() {
        // Create the database as it existed in version 1
        helper.createDatabase(notesDbTest, 1).close()

        // Run the auto migration and validate against exported schema
        helper.runMigrationsAndValidate(
            notesDbTest,
            2,
            true,
        )
    }

    @Test
    fun migrateFrom2To3_shouldMatchSchema() {
        // Create the database as it existed in version 1
        helper.createDatabase(notesDbTest, 2).close()

        // Run the auto migration and validate against exported schema
        helper.runMigrationsAndValidate(
            notesDbTest,
            3,
            true,
        )
    }
}
