package ru.toroschin.game.sensors;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import ru.toroschin.game.UserDataType;

public class Sensor implements ContactListener {
    private int counter;
    private boolean badContact = false;
    private int ladderCounter;

    public boolean isOnGround() {
        return counter > 0;
    }

    public boolean isOnLadder() {
        return ladderCounter > 0;
    }

    public boolean isBadContact() {
        return badContact;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        String s1 = fa.getUserData() == null ? "" : (String) fa.getUserData();
        String s2 = fb.getUserData() == null ? "" : (String) fb.getUserData();

        if ((s1.equals(UserDataType.sensor.name())) || s2.equals(UserDataType.sensor.name())) counter++;

        if ((s1.equals(UserDataType.heroCircle.name()) || s2.equals(UserDataType.heroCircle.name())) && (s1.equals(UserDataType.circle.name()) || s2.equals(UserDataType.circle.name()))) {
            if (s1.equals(UserDataType.circle.name()) && !fa.getBody().isAwake()) fa.getBody().setAwake(true);
            if (s2.equals(UserDataType.circle.name()) && !fb.getBody().isAwake()) fb.getBody().setAwake(true);
        }

        if ((s1.equals(UserDataType.hero.name()) || s2.equals(UserDataType.hero.name())) && (s1.equals(UserDataType.upCircle.name()) || s2.equals(UserDataType.upCircle.name()))) {
            badContact = true;
        }

        if ((s1.equals(UserDataType.ladder.name())) || s2.equals(UserDataType.ladder.name())) ladderCounter++;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        String s1 = fa.getUserData() == null ? "" : (String) fa.getUserData();
        String s2 = fb.getUserData() == null ? "" : (String) fb.getUserData();

        if ((s1.equals(UserDataType.sensor.name())) || s2.equals(UserDataType.sensor.name())) counter--;

        if ((s1.equals(UserDataType.ladder.name())) || s2.equals(UserDataType.ladder.name())) ladderCounter--;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
