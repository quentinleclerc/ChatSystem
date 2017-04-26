package model;

import java.util.LinkedList;

public class MessageQueue {

    private LinkedList<Message> discussion;

    private User recipient;

    public MessageQueue(User recipient) {
        this.discussion = new LinkedList<>();
        this.recipient = recipient;
    }

    public LinkedList getDiscussion(){
        return discussion;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public Boolean addMessage(Message msg) {
        return discussion.add(msg);
    }

}

