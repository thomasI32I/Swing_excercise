package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Message;

/**
 * This is a sort of simulated message server
 *
 */
public class MessageServer implements Iterable<Message> {
	
	private Map<Integer, List<Message>> messageStorage;
	private List<Message> selected;
	
	/**
	 * Constructor
	 */
	public MessageServer() {
		messageStorage = new TreeMap<>();
		selected = new ArrayList<>();
		
		List<Message> list = new ArrayList<>();
		list.add(new Message("The cat is missing", "Have you seen Felix anywhere?"));
		list.add(new Message("See you later?", "Are we still meeting in the pub?"));
		
		messageStorage.put(0, list);
		
		list = new ArrayList<>();
		list.add(new Message("How about dinner later?", "Are you doing anything later on?"));
		
		messageStorage.put(1, list);
	}
	
	/**
	 * All messages of all selected servers are stored in selected.
	 * 
	 */
	public void setSelectedServers(Set<Integer> servers) {
		selected.clear();
		
		for (Integer id: servers) {
			if (messageStorage.containsKey(id)) {
				selected.addAll(messageStorage.get(id));
			}
		}
	}
	
	/**
	 * Adds messages to the server message storage.
	 * 
	 * @param serverId
	 * @param messages
	 */
	public void add(Integer serverId, List<Message> messages) {
		if (messageStorage.containsKey(serverId)) {
			messageStorage.get(serverId).addAll(messages);
		} else {
			messageStorage.put(serverId, messages);
		}
	}
	
	public int getMessageCount() {
		return selected.size();
	}

	@Override
	public Iterator<Message> iterator() {
		return new MessageIterator(new ArrayList<>(selected));
	}
	
	/**
	 * Inner class which provides iterator functionality
	 */
	private static class MessageIterator implements Iterator<Message> {
		
		private Iterator<Message> iterator;
		
		public MessageIterator(List<Message> messages) {
			iterator = messages.iterator();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Message next() {		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
			
			return iterator.next();
		}
			
		public void remove() {
			iterator.remove();
		}
	}
	
}


