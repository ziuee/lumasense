package me.luma.client.management.event.impl;

import me.luma.client.management.event.Event;

public class EventKey extends Event {

    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}