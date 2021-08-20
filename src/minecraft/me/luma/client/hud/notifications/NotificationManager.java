package me.luma.client.hud.notifications;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
	
	private static CopyOnWriteArrayList<Notification> pendingNotifications = new CopyOnWriteArrayList();
	private static Notification currentNotifcation = null;
	public Notification lastNotif = null;
	
	public Notification getLastNotif() {
		return this.lastNotif;
	}
	
	public CopyOnWriteArrayList<Notification> getNotifications() {
		return pendingNotifications;
	}
	
	public void setLastNotif(Notification lastNotif) {
		this.lastNotif = lastNotif;
	}
	
	public static void show(NotificationType type, String name, String text, int lenght) {
		Notification newNotification = new Notification(type, name, text, lenght);
		pendingNotifications.add(newNotification);
		newNotification.show();
	}

	public void removeFromList(int index) {
        pendingNotifications.remove(index);
    }

    public void removeFromList(Notification object) {
        pendingNotifications.remove(object);
    }

    public int getIndex(Notification notification) {
        return pendingNotifications.indexOf(notification);
    }

    public Notification getObject(int index) {
        return (Notification)pendingNotifications.get(index);
    }

    public static void notificationUpdate() {
        CopyOnWriteArrayList<Notification> notificationList = pendingNotifications;
        Iterator var1 = notificationList.iterator();

        Notification notification;
        while(var1.hasNext()) {
            notification = (Notification)var1.next();
            notification.resetOffset();
        }

        var1 = notificationList.iterator();

        while(var1.hasNext()) {
            notification = (Notification)var1.next();
            notification.updateOffset();
        }

        var1 = notificationList.iterator();

        while(var1.hasNext()) {
            notification = (Notification)var1.next();
            notification.render();
        }

    }
}
