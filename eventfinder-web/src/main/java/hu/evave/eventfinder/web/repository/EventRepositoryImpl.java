package hu.evave.eventfinder.web.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import hu.evave.eventfinder.web.model.Event;
import hu.evave.eventfinder.web.model.Event_;
import hu.evave.eventfinder.web.model.Location;
import hu.evave.eventfinder.web.model.Location_;
import hu.evave.eventfinder.web.model.QEvent;
import hu.evave.eventfinder.web.model.type.EventType;

public class EventRepositoryImpl implements EventRepositoryCustom {

	@Autowired
	private EntityManager em;

	@Override
	public List<Event> findByTypeLocationKeyword(List<EventType> types, Location location, String phrase) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Event> cq = cb.createQuery(Event.class);
		Root<Event> event = cq.from(Event.class);

		List<Predicate> predicates = new ArrayList<>();

		if (phrase != null && !phrase.isEmpty()) {
			String phraseExpression = "%" + phrase + "%";
			predicates.add(cb.like(event.get(Event_.name), phraseExpression));
		}

		predicates.add(cb.greaterThanOrEqualTo(event.get(Event_.startsAt), new Date()));

		if (location != null) {
			String country = location.getCountry();
			if (country != null) {
				predicates.add(cb.equal(event.get(Event_.location).get(Location_.country), location.getCountry()));
			}
		}

		if (types != null && !types.isEmpty()) {
			predicates.add(event.get(Event_.types).in(types));
		}

		cq.distinct(true);
		cq.where(cb.and(predicates.toArray(new Predicate[0])));

		return em.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Event> findByTypeLocationKeywordQueryDsl(List<EventType> types, Location location, String phrase) {

		JPAQuery<Event> query = new JPAQuery<>(em);
		QEvent event = QEvent.event;

		query.from(event).distinct();

		List<BooleanExpression> conditions = new ArrayList<>();

		BooleanExpression isFutureEvent = event.startsAt.after(new Date());
		conditions.add(isFutureEvent);

		if (phrase != null && !phrase.isEmpty()) {
			conditions.add(event.name.contains(phrase));
		}

		if (location != null) {
			String country = location.getCountry();
			if (country != null) {
				conditions.add(event.location.country.eq(country));
			}
		}

		if (types != null && !types.isEmpty()) {
			conditions.add(event.types.any().in(types));
		}

		for (BooleanExpression c : conditions) {
			query.where(c);
		}

		return query.createQuery().getResultList();
	}

	@Override
	public long count(com.querydsl.core.types.Predicate arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists(com.querydsl.core.types.Predicate arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Event> findAll(com.querydsl.core.types.Predicate arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findAll(OrderSpecifier<?>... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findAll(com.querydsl.core.types.Predicate arg0, Sort arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Event> findAll(com.querydsl.core.types.Predicate arg0, OrderSpecifier<?>... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Event> findAll(com.querydsl.core.types.Predicate arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Event findOne(com.querydsl.core.types.Predicate arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
