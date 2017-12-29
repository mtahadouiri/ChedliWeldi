package com.esprit.chedliweldi.Entities;

import com.stfalcon.chatkit.commons.models.IDialog;

import java.util.ArrayList;

/**
 * Created by Taha on 25/12/2017.
 */

public class Dialog  implements IDialog<Message> {

    private String id;
    private String dialogPhoto;
    private String dialogName;
    private ArrayList<Babysitter> users;
    private Message lastMessage;

    private int unreadCount;

    public Dialog(String id, String name, String photo,
                  ArrayList<Babysitter> users, Message lastMessage, int unreadCount) {

        this.id = id;
        this.dialogName = name;
        this.dialogPhoto = photo;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    public Dialog() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<Babysitter> getUsers() {
        return users;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDialogPhoto(String dialogPhoto) {
        this.dialogPhoto = dialogPhoto;
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    public void setUsers(ArrayList<Babysitter> users) {
        this.users = users;
    }
}