package id.my.osa.dicodingfundamentalandroidsubs1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class FavoriteEventEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "summary")
    val summary: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "image_logo")
    val imageLogo: String? = null,

    @ColumnInfo(name = "media_cover")
    val mediaCover: String? = null,

    @ColumnInfo(name = "link")
    val link: String? = null,

    @ColumnInfo(name = "owner_name")
    val ownerName: String? = null,

    @ColumnInfo(name = "city_name")
    val cityName: String? = null,

    @ColumnInfo(name = "quota")
    val quota: Int? = null,

    @ColumnInfo(name = "registrants")
    val registrants: Int? = null,

    @ColumnInfo(name = "begin_time")
    val beginTime: String? = null,

    @ColumnInfo(name = "end_time")
    val endTime: String? = null,

    @ColumnInfo(name = "category")
    val category: String? = null
)
