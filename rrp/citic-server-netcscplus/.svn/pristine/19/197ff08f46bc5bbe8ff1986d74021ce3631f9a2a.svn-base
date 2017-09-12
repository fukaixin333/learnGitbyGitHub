package com.citic.server.service.time;

import java.util.LinkedList;

/**
 * @param <E> the type of elements held in this collection
 * @author Liu Xuanfei
 * @date 2016年5月14日 下午12:06:00
 */
public class Queue<E> extends LinkedList<E> {
	private static final long serialVersionUID = -5860544688057843694L;
	
	private final int MAX_SIZE;
	
	public Queue() {
		MAX_SIZE = Integer.MAX_VALUE;
	}
	
	public Queue(int size) {
		this.MAX_SIZE = size;
	}
	
	/**
	 * Appends the specified element to the end of this list.
	 */
	@Override
	public void push(E e) {
		if (super.size() == MAX_SIZE) {
			super.removeFirst();
		}
		super.addLast(e);
	}
	
	/**
	 * Retrieves and removes the first element of this list.
	 * 
	 * @return the head of this list, or null if this list is empty
	 */
	@Override
	public E pop() {
		return super.poll();
	}
	
	/**
	 * Retrieves, but does not remove, the head (first element) of this list.
	 * 
	 * @return the head of this list, or null if this list is empty
	 */
	public E first() {
		return super.peek();
	}
	
	public boolean empty() {
		return super.isEmpty();
	}
	
	public int getMaxSize() {
		return this.MAX_SIZE;
	}
}