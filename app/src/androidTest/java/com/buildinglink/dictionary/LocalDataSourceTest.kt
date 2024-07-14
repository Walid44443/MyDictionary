package com.buildinglink.dictionary


import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.buildinglink.dictionary.data.database.WordDatabase
import com.buildinglink.dictionary.data.database.datasource.LocalDataSourceImpl
import com.buildinglink.dictionary.data.database.model.DatabasePhonetic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.whenever

class LocalDataSourceTest {

    private lateinit var database: WordDatabase
    private lateinit var dataSource: LocalDataSourceImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WordDatabase::class.java
        )
            .allowMainThreadQueries() // Allow main thread access for testing
            .build()
        dataSource =
            LocalDataSourceImpl(database.phoneticDao(), ioDispatcher = UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getDefinitionReturnsDataWhenPresentInDatabase() = runBlocking {
        val phonetics = listOf(DatabasePhonetic(text = "text", word = "word", audio = "audioUrl"))
        database.phoneticDao().insertPhonetics(phonetics)

        val retrievedPhonetics = dataSource.getDefinition("word")
        assertEquals(phonetics, retrievedPhonetics)
    }

    @Test
    fun saveDefinitionSuccessfullyInsertsDataIntoDatabase() = runBlocking {
        val phonetics = listOf(DatabasePhonetic(text = "text", word = "word", audio = "audioUrl"))
        dataSource.saveDefinition("test", phonetics)

        val retrievedPhonetics = dataSource.getDefinition("test")
        assertEquals(phonetics, retrievedPhonetics)
    }

}