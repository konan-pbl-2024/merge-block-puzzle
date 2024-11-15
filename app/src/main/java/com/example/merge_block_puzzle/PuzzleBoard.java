package com.example.merge_block_puzzle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;

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

    private void init(Context context) {
        this.setRowCount(BOARD_SIZE);
        this.setColumnCount(BOARD_SIZE);
        this.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        this.setUseDefaultMargins(true);
        this.setGravity(Gravity.CENTER);

        blocks = new PuzzleBlock[BOARD_SIZE][BOARD_SIZE];

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int blockSize = screenWidth / (BOARD_SIZE + 2);

        // ブロックを作成してグリッドに配置
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                final PuzzleBlock block = new PuzzleBlock(context);
                block.setPosition(j, i);
                block.setOnBlockClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickCount < MAX_CLICKS) {
                            int row = block.getPositionY();
                            int col = block.getPositionX();
                            blockListener(col, row);
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
                });
                blocks[i][j] = block;

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

    private void setGravity(int center) {
    }

    private void disableAllBlocks() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                blocks[i][j].setClickable(false);
            }
        }
    }

    public void blockListener(int i, int j) {
        int disappearedBlockCount = 0;

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

                if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE &&
                        !visited[newY][newX] && blocks[newY][newX].getBlockType() == targetColor) {
                    queue.add(new int[]{newY, newX});
                    visited[newY][newX] = true;
                }
            }
        }
        visited[j][i] = false;

        // クリックされたブロックのタイプを1つ上げる（最大タイプを超えないように）
        if (blocks[j][i].getBlockType() < TOTAL_BLOCK_TYPES - 1) {
            blocks[j][i].setBlockType(blocks[j][i].getBlockType() + 1);
        }

        // クリックされたブロックと同じ色のブロックを消す
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (visited[y][x]) {
                    blocks[y][x].setBlockType(-1);
                    disappearedBlockCount++;
                }
            }
        }

        // ブロックを下に詰める
        for (int x = 0; x < BOARD_SIZE; x++) {
            int emptyRow = BOARD_SIZE - 1;
            for (int y = BOARD_SIZE - 1; y >= 0; y--) {
                if (blocks[y][x].getBlockType() != -1) {
                    if (y != emptyRow) {
                        blocks[emptyRow][x].setBlockType(blocks[y][x].getBlockType());
                        blocks[y][x].setBlockType(-1);
                    }
                    emptyRow--;
                }
            }
        }

        // 新しいブロックを上に追加（初期の3色のみ）
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (blocks[y][x].getBlockType() == -1) {
                    blocks[y][x].setBlockType((int) (Math.random() * INITIAL_BLOCK_TYPES));
                }
            }
        }

        // スコアを更新（ブロックタイプが高いほど高得点）
        int blockValue = targetColor + 1; // ブロックタイプは0から始まるため+1
        score += blockValue * disappearedBlockCount * disappearedBlockCount;

        // スコア変更をリスナーに通知
        if (scoreChangeListener != null) {
            scoreChangeListener.onScoreChanged(score);
        }
    }

    public int getScore() {
        return score;
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
