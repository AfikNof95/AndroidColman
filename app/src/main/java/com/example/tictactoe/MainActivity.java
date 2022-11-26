package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    int currentPlayer = 0;
    int currentTurn = 0;
    boolean isGameActive = true;
    int[] gameState = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int[][] winStates = {
            //Cols
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            //Rows
            {0, 1, 2}, {6, 7, 8}, {3, 4, 5},
            //Diagonal
            {0, 4, 8}, {2, 4, 6}


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.playAgain).setOnClickListener(this::playAgain);
    }

    public void onTileClick(View view) {
        int selectedTileIndex = Integer.parseInt(view.getTag().toString());
        ImageView selectedTile = (ImageView) view;
        if (isGameActive && gameState[selectedTileIndex] == -1) {
            gameState[selectedTileIndex] = currentPlayer;
            if (currentPlayer == 0) {
                selectedTile.setImageResource(R.drawable.x);
                currentPlayer = 1;
                findViewById(R.id.xTurn).setVisibility(View.INVISIBLE);
                findViewById(R.id.oTurn).setVisibility(View.VISIBLE);
            } else {
                selectedTile.setImageResource(R.drawable.o);
                currentPlayer = 0;
                findViewById(R.id.xTurn).setVisibility(View.VISIBLE);
                findViewById(R.id.oTurn).setVisibility(View.INVISIBLE);
            }
            currentTurn++;

            for (int[] winState : winStates) {
                if (gameState[winState[0]] != -1 && gameState[winState[0]] == gameState[winState[1]]
                        && gameState[winState[1]] == gameState[winState[2]]) {
                    isGameActive = false;
                    int winner;
                    if (currentPlayer == 0) {
                        winner = R.drawable.owin;

                    } else {
                        winner = R.drawable.xwin;
                    }
                    showWinner(winner, winState);
                }
            }

            if (currentTurn == gameState.length) {
                showWinner(R.drawable.nowin, null);
            }


        }

    }

    protected void showWinner(int winner, int[] winState) {
        ImageView winnerLabel = findViewById(R.id.Winner);
        winnerLabel.setImageResource(winner);
        findViewById(R.id.xTurn).setVisibility(View.INVISIBLE);
        findViewById(R.id.oTurn).setVisibility(View.INVISIBLE);
        winnerLabel.setVisibility(View.VISIBLE);
        findViewById(R.id.playAgain).setVisibility(View.VISIBLE);

        if (winState != null) {
            ConstraintLayout tiles = findViewById(R.id.Tiles);
            ImageView firstTile = tiles.findViewWithTag(winState[0] + "");
            ImageView lastTile = tiles.findViewWithTag(winState[2] + "");


            ImageView winningLine = new ImageView(this);


            winningLine.setId(View.generateViewId());
            winningLine.setTag("winningLine");
            winningLine.setY(firstTile.getY());
            tiles.addView(winningLine, 0);
            tiles.getMeasuredHeight();
            ViewGroup.LayoutParams lp = winningLine.getLayoutParams();

            winningLine.setScaleType(ImageView.ScaleType.FIT_XY);


            //Col
            if (winState[1] - winState[0] == 3) {
                lp.height = tiles.getMeasuredHeight();
                lp.width = tiles.getMeasuredWidth();
                winningLine.setImageResource(R.drawable.mark3);
                winningLine.setX(firstTile.getX());

            }
            //Row
            else if (winState[1] - winState[0] == 1) {
                lp.height = tiles.getMeasuredHeight();
                lp.width = tiles.getMeasuredWidth();


                winningLine.setImageResource(R.drawable.mark5);
                winningLine.setRotation(-90);
                winningLine.setX(firstTile.getX() );
                winningLine.setY(firstTile.getY());

            }//Diagonal
            else if (winState[1] - winState[0] == 4 || winState[1] - winState[0] == 2) {
                lp.height = tiles.getMeasuredHeight();
                lp.width = tiles.getMeasuredWidth();
                winningLine.setX(firstTile.getX());
                winningLine.setY(firstTile.getY());
                if (winState[0] == 0) {
                    winningLine.setImageResource(R.drawable.mark1);
                } else {
                    winningLine.setX(lastTile.getX());
                    winningLine.setImageResource(R.drawable.mark2);

                }
            }
            winningLine.setLayoutParams(lp);
            winningLine.bringToFront();
        }
    }

    public void playAgain(View view) {
        currentPlayer = 0;
        currentTurn = 0;
        Arrays.fill(gameState, -1);
        ConstraintLayout tiles = findViewById(R.id.Tiles);
        for (int i = 0; i < tiles.getChildCount(); i++) {
            ImageView tile = (ImageView) tiles.getChildAt(i);
            tile.setImageResource(R.drawable.empty);
        }
        findViewById(R.id.xTurn).setVisibility(View.VISIBLE);
        findViewById(R.id.oTurn).setVisibility(View.INVISIBLE);
        findViewById(R.id.Winner).setVisibility(View.INVISIBLE);
        findViewById(R.id.playAgain).setVisibility(View.INVISIBLE);
        isGameActive = true;
    }
}