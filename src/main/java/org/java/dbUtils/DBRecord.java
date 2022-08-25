package org.java.dbUtils;

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.java.dao.Dao;
import repository.Invoker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DBRecord<T> implements Dao<T> {

    private Class<T> type;

    public DBRecord(Class<T> type) {
        this.type = type;
    }

    @Override
    public T get(Number id) {
        Session session = getSession();
        session.beginTransaction();
        T t = session.get(type, id);
        session.getTransaction().commit();
        return t;
    }

    @Override
    public List<T> getAll() {
        return getAllRecords(null);
    }

    @Override
    public List<T> getAll(SearchSpecification spec) {
        return getAllRecords(spec);
    }

    @Override
    public void update(T t) {
        transactionAction(() -> getSession().update(t));
    }

    @Override
    public void delete(T t) {
        transactionAction(() -> getSession().delete(t));
    }

    @Override
    public void insert(T t) {
        try {
            transactionAction(() -> getSession().save(t));
            transactionAction(() -> getSession().refresh(t));
        } catch (MappingException e) {
            throw new MappingException("Exception happens", e);
        }
    }

    private void transactionAction(Invoker invoker) {
        Session session = getSession();
        session.beginTransaction();
        invoker.invoke();
        session.getTransaction().commit();
    }

    private List<T> getAllRecords(SearchSpecification spec) {
        Session session = getSession();
        try {
            session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cr = cb.createQuery(type);
            Root<T> root = cr.from(type);
            cr.select(root);
            if (null != spec) cr.where(spec.toPredicate(root, cb));
            List<T> result = session.createQuery(cr).getResultList();
            session.close();
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }

    }

    private Session getSession(){
        return DBConnector.getSessionFactory().getCurrentSession();
    }
}
