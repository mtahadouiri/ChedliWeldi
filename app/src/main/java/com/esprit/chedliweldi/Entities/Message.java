package com.esprit.chedliweldi.Entities;

import android.support.annotation.NonNull;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;


/**
 * Created by Taha on 25/12/2017.
 */

public class Message implements Comparable<Message>,IMessage,
        MessageContentType.Image, /*this is for default image messages implementation*/
        MessageContentType /*and this one is for custom content type (in this case - voice message)*/ {

    private String id;
    private String text;
    private Date createdAt;
    private Babysitter user;
    private Image image;
    private Voice voice;

    public Message(String id, Babysitter user, String text) {
        this(id, user, text, new Date());
    }

    public Message(String id, Babysitter user, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Message() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public Babysitter getUser() {
        return this.user;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }

    public Voice getVoice() {
        return voice;
    }

    public String getStatus() {
        return "Sent";
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    @Override
    public int compareTo(@NonNull Message message) {
        return getCreatedAt().compareTo(message.getCreatedAt());
    }

    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(Babysitter user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public static class Voice {

        private String url;
        private int duration;

        public Voice(String url, int duration) {
            this.url = url;
            this.duration = duration;
        }

        public String getUrl() {
            return url;
        }

        public int getDuration() {
            return duration;
        }
    }
}
