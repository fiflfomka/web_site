package ru.th.DAO;

import ru.th.models.Actor;
import ru.th.models.ActorPK;

public class ActorDAO extends CommonDAO<Actor,ActorPK> {
    public ActorDAO() {
        super(Actor.class, ActorPK.class);
    }
}
