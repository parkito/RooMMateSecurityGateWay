package com.graphql.play.server.service

import com.graphql.play.server.converter.ActorConverter
import com.graphql.play.server.exception.ActorDoesNotExistException
import com.graphql.play.server.model.Actor
import com.graphql.play.server.model.page.PageQuery
import com.graphql.play.server.model.page.PagedContent
import com.graphql.play.server.repository.ActorRepository
import com.graphql.play.server.utils.convertPageQuery
import com.graphql.play.server.utils.toPagedContent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ActorService(
        val actorRepository: ActorRepository,
        val actorConverter: ActorConverter) {

    fun findById(id: Long): Actor {
        return actorRepository.findByIdOrNull(id)?.let {
            actorConverter.convertEntity(it)
        } ?: throw ActorDoesNotExistException("Actor with id = $id doesn't exist")
    }

    fun updateAvatar(id: Long, url: String) {
        actorRepository.updateAvatar(id, url)
    }

    fun findAll(query: PageQuery): PagedContent<Actor> {
        val page = actorRepository.findAll(convertPageQuery(query))
        return toPagedContent(page, actorConverter.convertEntities(page.content))
    }
}