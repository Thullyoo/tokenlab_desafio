package br.thullyoo.event_back.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "TB_USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String document;

    private String password;

    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> eventsCreated = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invite> receivedInvites = new ArrayList<>();

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Event> getEventsCreated() {
        return eventsCreated;
    }

    public void setEventsCreated(Set<Event> eventsCreated) {
        this.eventsCreated = eventsCreated;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public List<Invite> getReceivedInvites() {
        return receivedInvites;
    }

    public void setReceivedInvites(List<Invite> receivedInvites) {
        this.receivedInvites = receivedInvites;
    }

    @Override
    public String toString() {
        return "User{" +
                "document='" + document + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
