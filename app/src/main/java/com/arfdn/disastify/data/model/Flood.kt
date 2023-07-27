package com.arfdn.disastify.data.model

import com.google.gson.Gson

data class GeometryDummy(
    val type: String,
    val geometries: List<Polygon>
)

data class Polygon(
    val type: String,
    val properties: PropertiesDummy,
    val arcs: List<List<Int>>
)

data class PropertiesDummy(
    val area_id: String,
    val geom_id: String,
    val area_name: String,
    val parent_name: String,
    val city_name: String,
    val state: Int,
    val last_updated: String
)

val dummyGeometry = GeometryDummy(
    type = "Topology",
    geometries = listOf(
        Polygon(
            type = "Polygon",
            properties = PropertiesDummy(
                area_id = "5",
                geom_id = "3174040004009000",
                area_name = "RW 09",
                parent_name = "GROGOL",
                city_name = "Jakarta",
                state = 1,
                last_updated = "2016-12-19T13:53:52.274Z"
            ),
            arcs = listOf(
                listOf(0)
            )
        )
    )
)

val gson = Gson()
val dummyJson = gson.toJson(dummyGeometry)