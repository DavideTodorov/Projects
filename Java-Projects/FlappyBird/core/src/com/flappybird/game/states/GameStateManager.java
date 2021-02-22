package com.flappybird.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayDeque;

public class GameStateManager {
    private ArrayDeque<State> states;

    public GameStateManager() {
        this.states = new ArrayDeque<>();
    }

    //Method to add new state
    public void push(State state) {
        this.states.push(state);
    }

    //Method to remove the state on the top
    public void pop() {
        states.pop();
    }

    //Method to first remove the top state and then add a new one
    public void set(State state) {
        this.pop();
        this.push(state);
    }

    public void update(float deltaTime) {
        states.peek().update(deltaTime);
    }

    public void render(SpriteBatch spriteBatch) {
        states.peek().render(spriteBatch);
    }
}
