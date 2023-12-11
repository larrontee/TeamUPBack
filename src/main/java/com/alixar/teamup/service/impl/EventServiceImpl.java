package com.alixar.teamup.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alixar.teamup.model.EventEntity;
import com.alixar.teamup.repository.EventRepository;
import com.alixar.teamup.repository.TeamRepository;

@Service
public class EventServiceImpl {

	@Autowired
	EventRepository eventRepository;
	@Autowired
	TeamRepository teamRepository;

	public EventEntity createEvent(EventEntity event) {
		return eventRepository.save(event);
	}

	public boolean deleteEvent(Long id) {
		eventRepository.deleteById(id);
		return !eventRepository.existsById(id);
	}

	public EventEntity getById(Long id) {
		EventEntity optionalEvent = eventRepository.findById(id).get();
		return optionalEvent;
	}

	public EventEntity updateEvent(Long id, EventEntity event) {
		EventEntity exist = eventRepository.findById(id).get();
		if (exist != null) {
			return eventRepository.save(event);
		}
		return null;
	}



	public Set<EventEntity> getAllEvents() {
		Iterable<EventEntity> allEventsIterable = eventRepository.findAll(Sort.by(Sort.Direction.ASC, "startDate"));
		Set<EventEntity> allEvents = new HashSet<>();
		allEventsIterable.forEach(allEvents::add);
		return allEvents;
	}

	public Set<EventEntity> getEventosPorTipo(String tipo) {
		Iterable<EventEntity> allEventsIterable = eventRepository.findByTipoEvent(tipo,
				Sort.by(Sort.Direction.ASC, "startDate"));
		Set<EventEntity> allEvents = new HashSet<>();
		allEventsIterable.forEach(allEvents::add);
		return allEvents;
	}

	public Set<EventEntity> getEventosPorFecha(Date date) {
		Iterable<EventEntity> allEventsIterable = eventRepository.findByDate(date,
				Sort.by(Sort.Direction.ASC, "startDate"));
		Set<EventEntity> allEvents = new HashSet<>();
		allEventsIterable.forEach(allEvents::add);
		return allEvents;
	}


//	public Set<EventEntity> getAllEvents(EventEntity event, Pageable pageable) {
//		Page<EventEntity> eventPage = eventRepository.findAll(pageable);
//		Set<EventEntity> allEvents = new HashSet<>(eventPage.getContent());
//		return allEvents;
//	}

}
