package com.example.merge_block_puzzle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;

public class PuzzleBoard extends GridLayout {
    private static final int BOARD_SIZE = 6;
    private static final int MAX_CLICKS = 20; // 最大クリック回数を20に変更
    private int clickCount = 0; // 現在のクリック数

    private PuzzleBlock[][] blocks;
    private static final int TOTAL_BLOCK_TYPES = 6; // ブロックタイプの総数（0～5）
    private static final int INITIAL_BLOCK_TYPES = 3; // 初期生成されるブロックタイプ数（0～2）
    private int score = 0;
    private OnScoreChangeListener scoreChangeListener;
    private OnClicksRemainingListener clicksRemainingListener;

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

    public int getScore() {
        return score;
    }

    private void init(Context context) {
        this.setRowCount(BOARD_SIZE);
        this.setColumnCount(BOARD_SIZE);
        this.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        this.setUseDefaultMargins(true);

        blocks = new PuzzleBlock[BOARD_SIZE][BOARD_SIZE];

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int blockSize = screenWidth / (BOARD_SIZE + 2);

        // ブロックを作成してグリッドに配置
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                final PuzzleBlock block = new PuzzleBlock(context);
                block.setPosition(col, row);
                block.setOnBlockClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickCount < MAX_CLICKS) {
                            int row = block.getPositionY();
                            int col = block.getPositionX();
                            boolean validMove = blockListener(row, col);
                            if (validMove) {
                                clickCount++;
                                if (clicksRemainingListener != null) {
                                    clicksRemainingListener.onClicksRemaining(MAX_CLICKS - clickCount);
                                }
                                if (clickCount >= MAX_CLICKS) {
                                    // クリック制限に達した場合、全てのブロックのクリックを無効化
                                    disableAllBlocks();
                                }
                            }
                        }
                    }
                });
                blocks[row][col] = block;

                // ブロックのサイズを設定
                GridLayout.LayoutParams blockParams = new GridLayout.LayoutParams();
                blockParams.width = blockSize;
                blockParams.height = blockSize;
                blockParams.setMargins(4, 4, 4, 4);
                block.setLayoutParams(blockParams);

                this.addView(block);
            }
        }
    }

    private void disableAllBlocks() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                blocks[row][col].setClickable(false);
            }
        }
    }

    public boolean blockListener(int row, int col) {
        // 消えたブロックの数を初期化（クリックしたブロックは除外するため0）
        int disappearedBlockCount = 0;

        // クリックされたブロックと同じ色のブロックを探す
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        Queue<int[]> queue = new LinkedList<>();
        int[] directions = {-1, 0, 1, 0, -1};
        int targetColor = blocks[row][col].getBlockType();

        queue.add(new int[]{row, col});
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0]; // row
            int c = current[1]; // col

            // クリックしたブロックは除外
            if (r != row || c != col) {
                disappearedBlockCount++;
            }

            for (int d = 0; d < 4; d++) {
                int newR = r + directions[d];
                int newC = c + directions[d + 1];

                if (newR >= 0 && newR < BOARD_SIZE && newC >= 0 && newC < BOARD_SIZE &&
                        !visited[newR][newC] && blocks[newR][newC].getBlockType() == targetColor) {
                    queue.add(new int[]{newR, newC});
                    visited[newR][newC] = true;
                }
            }
        }

        // 消えるブロックが1つ未満の場合（クリックしたブロックを除外しているため）
        if (disappearedBlockCount < 1) {
            // 2つ以上つながっていないと消せないことをToastで表示
            Toast.makeText(getContext(), "2つ以上つなげてください", Toast.LENGTH_SHORT).show();
            return false; // 操作は無効
        }

        // クリックされたブロックのタイプを1つ上げる（最大タイプを超えないように）
        if (blocks[row][col].getBlockType() < TOTAL_BLOCK_TYPES - 1) {
            blocks[row][col].setBlockType(blocks[row][col].getBlockType() + 1);
        }

        // クリックされたブロックと同じ色のブロックを消す（クリックしたブロックは除外）
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (visited[r][c] && !(r == row && c == col)) {
                    blocks[r][c].setBlockType(-1);
                }
            }
        }

        // ブロックを下に詰める
        for (int colIdx = 0; colIdx < BOARD_SIZE; colIdx++) {
            int emptyRow = BOARD_SIZE - 1;
            for (int rowIdx = BOARD_SIZE - 1; rowIdx >= 0; rowIdx--) {
                if (blocks[rowIdx][colIdx].getBlockType() != -1) {
                    if (rowIdx != emptyRow) {
                        blocks[emptyRow][colIdx].setBlockType(blocks[rowIdx][colIdx].getBlockType());
                        blocks[rowIdx][colIdx].setBlockType(-1);
                    }
                    emptyRow--;
                }
            }
        }

        // 新しいブロックを上に追加（初期の3色のみ）
        for (int colIdx = 0; colIdx < BOARD_SIZE; colIdx++) {
            for (int rowIdx = 0; rowIdx < BOARD_SIZE; rowIdx++) {
                if (blocks[rowIdx][colIdx].getBlockType() == -1) {
                    blocks[rowIdx][colIdx].setBlockType((int) (Math.random() * INITIAL_BLOCK_TYPES));
                }
            }
        }

        // スコアを更新（ブロックタイプが高いほど高得点）
        int blockValue = targetColor + 1; // ブロックタイプは0から始まるため+1
        score += blockValue * (disappearedBlockCount + 1) * (disappearedBlockCount + 1);

        // スコア変更をリスナーに通知
        if (scoreChangeListener != null) {
            scoreChangeListener.onScoreChanged(score);
        }

        return true; // 操作は有効
    }

    public void setOnScoreChangeListener(OnScoreChangeListener listener) {
        this.scoreChangeListener = listener;
    }

    public interface OnScoreChangeListener {
        void onScoreChanged(int newScore);
    }

    public void setOnClicksRemainingListener(OnClicksRemainingListener listener) {
        this.clicksRemainingListener = listener;
    }

    public interface OnClicksRemainingListener {
        void onClicksRemaining(int clicksRemaining);
    }
}
