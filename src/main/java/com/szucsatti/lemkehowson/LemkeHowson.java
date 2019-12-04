package com.szucsatti.lemkehowson;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class LemkeHowson {

    private Matrix payoffRowPlayer;
    private Matrix payoffColumnPlayer;

    private Random random;
    private TableuxContainer tableuxContainer;

    public LemkeHowson (final Matrix payoffRowPlayer, final Matrix payoffColumnPlayer){
        assert payoffRowPlayer.getRows() == payoffColumnPlayer.getRows();
        assert payoffRowPlayer.getCols() == payoffColumnPlayer.getCols();
        this.payoffRowPlayer = payoffRowPlayer;
        this.payoffColumnPlayer = payoffColumnPlayer;
        this.random = new Random();
    }

    public void algorithm(){
        Matrix[] matrices = normalize();
        createTableaux(matrices[0], matrices[1]);

//        int startingLabel = getRandomLabel();
        int startingLabel = 1;

        this.tableuxContainer.findByLabel(startingLabel).ifPresent(tableux -> {
            pivot(tableux, startingLabel);
        });
    }

    private void pivot(final Tableux tableux, final int label){
        final Tableux newTableux = tableux.pivot(label);
        this.tableuxContainer.setCurrent(newTableux);
        final Tableux other = this.tableuxContainer.other();
        findDuplicateLabel(other.getLabels(), newTableux.getLabels()).ifPresent(duplicate -> {
            pivot(other, duplicate);
        });
    }

    private Optional<Integer> findDuplicateLabel(final List<Integer> oldLabels, final List<Integer> newLabels){
        for(Iterator<Integer> it = newLabels.iterator(); it.hasNext();){
            Integer label = it.next();
            if(oldLabels.contains(label)){
                return Optional.of(label);
            }
        }
        return Optional.empty();
    }


    private int getRandomLabel(){
        int upperBound = payoffRowPlayer.getCols() + payoffColumnPlayer.getCols();
        int label = random.nextInt(upperBound);
        assert label >= 0;
        assert label < upperBound;
        return label;
    }

    public Matrix[] normalize(){
        Matrix joined = this.payoffRowPlayer.switchRowsWithCols().join(this.payoffColumnPlayer);
        Matrix joinedNormalized = joined.normalize();
        return joinedNormalized.split();
    }

    public void createTableaux(final Matrix normPayoffRowPlayer, final Matrix normPayoffColumnPlayer){
        Tableux rowPlayer = Tableux.builder()
                .matrix(normPayoffRowPlayer)
                .identity()
                .build();
        Tableux columnPlayer = Tableux.builder()
                .identity()
                .matrix(normPayoffColumnPlayer)
                .build();
        this.tableuxContainer = new TableuxContainer(rowPlayer, columnPlayer);
    }

    public void print(){
        this.tableuxContainer.rowPlayer().getMatrix().print();
        System.out.println();
        this.tableuxContainer.columnPlayer().getMatrix().print();
    }

}
