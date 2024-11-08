package com.example.merge_block_puzzle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;

public class PuzzleBoard extends GridLayout {
    private static final int BOARD_SIZE = 5;
    private PuzzleBlock[][] blocks;
    private int currentBlockTypeCount = 3;

    public PuzzleBoard(Context context) {
        super(context);
        init(context);
    }

    public PuzzleBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PuzzleBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setRowCount(BOARD_SIZE);
        this.setColumnCount(BOARD_SIZE);
        blocks = new PuzzleBlock[BOARD_SIZE][BOARD_SIZE];



        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                // blocks[i][j] = new PuzzleBlock(context);
                // this.addView(blocks[i][j]);
                PuzzleBlock block = new PuzzleBlock(context);
                block.setPosition(j, i);
                block.setOnBlockClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int row = block.getPositionY();
                        int col = block.getPositionX();
                        blockListener(col, row);
                    }
                });
                blocks[i][j] = block;
                this.addView(block);
            }
        }
    }
    /*
        Button okButton = (Button)findViewById(R.id.button);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText);
                String inputStr = editText.getText().toString();
                TextView textView1 = (TextView)findViewById(R.id.text);
                textView1.setText(inputStr);
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText);
                String inputStr = editText.getText().toString();
                TextView textView1 = (TextView)findViewById(R.id.text);
                textView1.setText(inputStr);
            }
        });
     */

    public void blockListener(int i, int j) {
        /*
         * タップを通知する
         * 幅優先探索を使って、クリックされたブロックと同じ色のブロックを探す
         * その後、クリックされたブロックと同じ色のブロックを消す
         * そして、上にあるブロックを下に詰める
         * そして、新しいブロックを上に追加する
         */

        // タップを通知する
//        Toast.makeText(getContext(), "PuzzleBlockがクリックされました", Toast.LENGTH_SHORT).show();

        // 色をデバッグ出力する
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print(blocks[y][x].getBlockType());
            }
            System.out.println();
        }

        // クリックされたブロックと同じ色のブロックを探す
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        Queue<int[]> queue = new LinkedList<>();
        int[] directions = {-1, 0, 1, 0, -1};
        int targetColor = blocks[j][i].getBlockType();

        queue.add(new int[]{j, i});
        visited[j][i] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[1];
            int y = current[0];

            for (int d = 0; d < 4; d++) {
                int newX = x + directions[d];
                int newY = y + directions[d + 1];

                if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE && !visited[newY][newX] && blocks[newY][newX].getBlockType() == targetColor) {
                    queue.add(new int[]{newY, newX});
                    visited[newY][newX] = true;
                }
            }
        }

        // visitedをデバッグ出力する
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print(visited[y][x] ? "T" : "F");
            }
            System.out.println();
        }


        // クリックされたブロックと同じ色のブロックを消す
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (visited[y][x]) {
                    blocks[y][x].printInfo();
//                    this.removeView(blocks[y][x]);
                    blocks[y][x].setBlockType(-1);

                }
            }
        }

        // 上にあるブロックを下に詰める
        for (int k = 0; k < BOARD_SIZE; k++) {
            for (int y = 0; y < BOARD_SIZE - 1; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if (blocks[y][x].getBlockType() != -1 && blocks[y + 1][x].getBlockType() == -1) {
                        // for (int k = y - 1; k >= 0; k--) {
                        //     if (blocks[k][x] != null) {
                        //         blocks[y][x] = blocks[k][x];
                        //         blocks[y][x].setPosition(x, y);
                        //         blocks[k][x] = null;
                        //         break;
                        //     }
                        // }
                        // blocks[y][x] = blocks[y - 1][x];
                        // blocks[y][x].setPosition(x, y);
                        // blocks[y - 1][x] = null;
                        blocks[y + 1][x].setBlockType(blocks[y][x].getBlockType());
                        blocks[y][x].setBlockType(-1);
                        //                    System.out.println("swap: " + x + ", " + y + " and " + x + ", " + (y - 1));
                    }
                }
            }
        }

        // // 新しいブロックを上に追加する
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (blocks[y][x].getBlockType() == -1) {
                    // blocks[y][x] = block;
                    // this.addView(block);
                    blocks[y][x].setBlockType((int) (Math.random() * currentBlockTypeCount));
//                    System.out.println("add: " + x + ", " + y + " color: " + blocks[y][x].getBlockType());
                }
            }
        }

        // 色をデバッグ出力する
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print(blocks[y][x].getBlockType());
            }
            System.out.println();
        }
    }

    public PuzzleBlock getBlock(int row, int col) {
        return blocks[row][col];
    }

    public void setBlock(int row, int col, PuzzleBlock block) {
        blocks[row][col] = block;
        this.addView(block);
    }
}