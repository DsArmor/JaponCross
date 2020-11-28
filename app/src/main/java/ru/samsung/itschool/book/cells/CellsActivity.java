package ru.samsung.itschool.book.cells;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;

import task.Stub;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class CellsActivity extends Activity implements OnClickListener {

    private final int WIDTH = 14;
    private final int HEIGHT = 14;
    int score=0, check_score=0;
    private Button[][] cells;
    final boolean [][] type_cell = new boolean[HEIGHT][WIDTH];
    final boolean [][] type_cell_check = new boolean[HEIGHT][WIDTH];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cells);
        makeCells();
        generate();

    }
    @SuppressLint("SetTextI18n")
    void reset(){
        for (int i = 4; i < HEIGHT; i++){
            int sum=0, check_point=0;
            for (int j = 4; j < WIDTH; j++) {
                if (type_cell[i][j]){
                    sum++;
                }
                else{
                    if (sum>0){
                        cells[i][check_point].setText(sum+"");
                        check_point++;
                    }
                    sum=0;
                }
            }
            if (sum>0){
                cells[i][check_point].setText(sum+"");
            }

        }
        for (int j=4; j<WIDTH; j++){
            int sum=0, check_point=0;
            for (int i=4; i<HEIGHT; i++){
                if (type_cell[i][j])
                    sum++;
                else{
                    if (sum>0){
                    cells[check_point][j].setText(sum+"");
                    check_point++;
                    }
                    sum=0;
                }
            }
            if (sum>0){
                cells[check_point][j].setText(sum+"");
            }
        }

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                cells[i][j].setBackgroundColor(Color.WHITE);
                type_cell[i][j]=false;
            }
        for (int i=0; i<5; i++)
            for (int j=0; j<5; j++){
                cells[i][j].setBackgroundColor(Color.GRAY);
            }
        cells[2][2].setBackgroundColor(Color.BLACK);
    }

    @SuppressLint("SetTextI18n")
    void generate() {

        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                cells[i][j].setBackgroundColor(Color.WHITE);
            }
        for (int i=0; i<5; i++)
            for (int j=0; j<5; j++){
                cells[i][j].setBackgroundColor(Color.GRAY);
            }
        cells[2][2].setBackgroundColor(Color.WHITE);
    }
    @Override
    public void onClick(View v) {
        Button tappedCell = (Button) v;
        int tappedX = getX(tappedCell);
        int tappedY = getY(tappedCell);
        if (tappedX==2 && tappedY==2){
            type_cell[2][2]=true;
            check_score--;
            reset();
        }

        if (!type_cell[tappedY][tappedX]){
            if (type_cell[2][2]){
                if (type_cell_check[tappedY][tappedX])
                    score++;
            }
            else{
                type_cell_check[tappedY][tappedX]=true;
                check_score++;
            }
            cells[tappedY][tappedX].setBackgroundColor(BLACK);
            type_cell[tappedY][tappedX]=true;
        }
        else{
            if (type_cell[2][2]){
                if (type_cell_check[tappedY][tappedX])
                    score--;
            }
            else{
                type_cell_check[tappedY][tappedX]=false;
                check_score--;
            }
            cells[tappedY][tappedX].setBackgroundColor(WHITE);
            type_cell[tappedY][tappedX]=false;
        }
        if (score==check_score && type_cell[2][2])
            Stub.show(this, "Вы выиграли");
        cells[0][0].setText(check_score+" ");
    }
    int getX(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[1]);
    }
    int getY(View v) {
        return Integer.parseInt(((String) v.getTag()).split(",")[0]);
    }
    void makeCells() {
        cells = new Button[HEIGHT][WIDTH];
        GridLayout cellsLayout = (GridLayout) findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(WIDTH);
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                cells[i][j] = (Button) inflater.inflate(R.layout.cell, cellsLayout, false);
                if ((i>4 && j>4) || (i==2 && j==2))
                    cells[i][j].setOnClickListener(this);
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
    }
}