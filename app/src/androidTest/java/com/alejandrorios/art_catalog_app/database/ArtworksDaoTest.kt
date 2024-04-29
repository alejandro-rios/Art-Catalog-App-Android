package com.alejandrorios.art_catalog_app.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alejandrorios.art_catalog_app.data.db.ArtworksDao
import com.alejandrorios.art_catalog_app.data.db.ArtworksDatabase
import com.alejandrorios.art_catalog_app.domain.models.Artwork
import java.io.IOException
import java.util.concurrent.CountDownLatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Is much better to run this as instrumented tests (androidTest) with an in-memory database rather as an Unit test. That way we
 * can validate much more of the real logic
 */
@RunWith(AndroidJUnit4::class)
class ArtworksDaoTest {

    private lateinit var database: ArtworksDatabase
    private lateinit var artworksDao: ArtworksDao

    @Before
    fun setupDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            ArtworksDatabase::class.java
        ).allowMainThreadQueries().build()

        artworksDao = database.dao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertArtwork_returnsTrue() = runBlocking {
        val mockedArtwork = Artwork(
            id = 12472,
            title = "Stone",
            artworkTypeTitle = "Sculpture",
            artistTitle = "Joan Miró",
            imageUrl = "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
        )

        artworksDao.insertArtwork(mockedArtwork)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            artworksDao.getArtworks().collect {
                assertThat(it[0], equalTo(mockedArtwork))
                latch.countDown()

            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    @Throws(Exception::class)
    fun findArtworkById_returnsTrue() = runBlocking {
        val mockedArtwork = Artwork(
            id = 12472,
            title = "Stone",
            artworkTypeTitle = "Sculpture",
            artistTitle = "Joan Miró",
            imageUrl = "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
        )

        artworksDao.insertArtwork(mockedArtwork)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            artworksDao.findArtworkById(12472).collect {
                assertThat(it, equalTo(mockedArtwork))
                latch.countDown()

            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    @Throws(Exception::class)
    fun findArtworkById_returnsFalse() = runBlocking {
        val mockedArtwork = Artwork(
            id = 12472,
            title = "Stone",
            artworkTypeTitle = "Sculpture",
            artistTitle = "Joan Miró",
            imageUrl = "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
        )

        artworksDao.insertArtwork(mockedArtwork)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            artworksDao.findArtworkById(31244).collect {
                assertThat(it, equalTo(null))
                latch.countDown()

            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    @Throws(Exception::class)
    fun delete_returnsTrue() = runBlocking {
        val mockedArtwork1 = Artwork(
            id = 12472,
            title = "Stone",
            artworkTypeTitle = "Sculpture",
            artistTitle = "Joan Miró",
            imageUrl = "https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
        )
        val mockedArtwork2 = Artwork(
            id = 9573894,
            title = "Lake",
            artworkTypeTitle = "Paint",
            artistTitle = "Pepito perez",
            imageUrl = "https://www.artic.edu/iiif/2/e964499b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/843,/0/default.jpg",
        )

        artworksDao.insertArtwork(mockedArtwork1)
        artworksDao.insertArtwork(mockedArtwork2)

        artworksDao.deleteArtwork(mockedArtwork1)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            artworksDao.getArtworks().collect {
                assertThat(it[0], equalTo(mockedArtwork2))
                latch.countDown()

            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}
