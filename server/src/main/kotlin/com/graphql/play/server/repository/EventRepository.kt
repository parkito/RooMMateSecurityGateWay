package com.graphql.play.server.repository

import com.graphql.play.server.entity.ActorEntity
import com.graphql.play.server.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {

    @Query(value = "from EventEntity e order by e.id")
    fun findAllOrderById(): List<EventEntity>

    fun findByActorIdOrderById(id: Long?): List<EventEntity>

    @Query(value = "SELECT e FROM EventEntity e LEFT JOIN FETCH e.actor " + "ORDER BY e.actor.id, e.creationDate")
    fun findAllForActorsOrderByActorIdAndCreatedAt(): List<EventEntity>
}