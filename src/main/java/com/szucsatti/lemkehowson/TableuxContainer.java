package com.szucsatti.lemkehowson;

import java.util.Optional;

public class TableuxContainer {

    private Tableux[] container = new Tableux[2];

    private int cursor = 0;

    private static final int ROW_PLAYER = 0;

    private static final int COLUMN_PLAYER = 1;

    public TableuxContainer(final Tableux rowPlayer, final Tableux columnPlayer) {
        this.container[ROW_PLAYER] = rowPlayer ;
        this.container[COLUMN_PLAYER] = columnPlayer;
    }

    private void toggleCursor(){
        if(this.cursor == ROW_PLAYER){
            this.cursor = COLUMN_PLAYER;
        } else {
            this.cursor = ROW_PLAYER;
        }
    }

    public Tableux other(){
        toggleCursor();
        return this.container[this.cursor];
    }

    public void setCurrent(final Tableux tableux){
        this.container[this.cursor] = tableux;
    }

    public Optional<Tableux> findByLabel(final int label){
        if(rowPlayer().hasLabel(label)){
            this.cursor = ROW_PLAYER;
            return Optional.of(rowPlayer());
        } else if(columnPlayer().hasLabel(label)){
            this.cursor = COLUMN_PLAYER;
            return Optional.of(columnPlayer());
        }
        return Optional.empty();
    }

    private Tableux rowPlayer(){
        return this.container[ROW_PLAYER];
    }

    private Tableux columnPlayer(){
        return this.container[COLUMN_PLAYER];
    }

    public void replaceCurrent(final Tableux tableux){
        this.container[this.cursor] = tableux;
    }
}
