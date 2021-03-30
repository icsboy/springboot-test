package com.approval.approval.repository;

import com.approval.approval.domain.Document;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaDocumentRepository implements DocumentRepository{

    private final EntityManager em;

    public JpaDocumentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Document save(Document document) {
        em.persist(document);
        return document;
    }

    @Override
    public Optional<Document> findById(Long id) {
        Document document = em.find(Document.class, id);
        return Optional.ofNullable(document);
    }

    @Override
    public List<Document> findByCreatorId(Long creatorid) {
        List<Document> result = em.createQuery("select d from Document d where d.creatorid = :creatorid", Document.class)
                .setParameter("creatorid", creatorid)
                .getResultList();

        return result;
    }

    @Override
    public List<Document> findByApproverId(Long approverId) {
        List<Document> result = em.createQuery("select d from Document d where d.approverId = :approverId", Document.class)
                .setParameter("approverId", approverId)
                .getResultList();

        return result;
    }

    @Override
    public List<Document> findAll() {
        return em.createQuery("select d from Document d", Document.class)
                .getResultList();
    }

    @Override
    public int deleteByIdAndCreatorId(Long id, Long creatorId) {
        return em.createQuery("delete from Document d where d.id = :id and d.creatorId = :creatorId", Document.class)
                .setParameter("id", id)
                .setParameter("creatorId", creatorId)
                .executeUpdate();
    }

    @Override
    public void delete(Document document) {
        em.remove(document);
    }
}
