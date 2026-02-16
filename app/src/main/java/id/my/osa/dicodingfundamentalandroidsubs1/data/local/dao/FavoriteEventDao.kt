package id.my.osa.dicodingfundamentalandroidsubs1.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.my.osa.dicodingfundamentalandroidsubs1.data.local.entity.FavoriteEventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteEventDao {

    @Query("SELECT * FROM favorite_events ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<FavoriteEventEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_events WHERE id = :id)")
    fun isFavorite(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: FavoriteEventEntity)

    @Query("DELETE FROM favorite_events WHERE id = :id")
    suspend fun deleteById(id: Int)
}
