package com.woonit.wonnit.global.entity

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import kotlin.jvm.Transient
import kotlin.jvm.javaClass

@MappedSuperclass
abstract class PrimaryKeyEntity : Persistable<UUID> {

    @Id
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private val id: UUID = UlidCreator.getMonotonicUlid().toUuid()

    @Transient
    private var isNewEntity: Boolean = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = isNewEntity

    override fun hashCode(): Int = Objects.hashCode(id)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false

        val thisClass = unwrapProxy(this).javaClass
        val otherClass = unwrapProxy(other).javaClass
        if (thisClass != otherClass) return false

        return id == getIdentifier(other)
    }

    private fun unwrapProxy(entity: Any): Any =
        if (entity is HibernateProxy) entity.hibernateLazyInitializer.implementation else entity

    private fun getIdentifier(entity: Any): Serializable? =
        if (entity is HibernateProxy) {
            entity.hibernateLazyInitializer.identifier as? Serializable
        } else {
            (entity as PrimaryKeyEntity).id
        }

    @PostPersist
    @PostLoad
    protected fun markNotNew() {
        isNewEntity = false
    }
}