package com.example.merge_block_puzzle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("AppCompatCustomView")
public class PuzzleBlock extends ImageButton {
    private int blockType;
    private static final int[] imageResourceIds = {
            R.drawable.block1,
//            R.drawable.block2,
            R.drawable.block3,
            R.drawable.block4,
    };
    private static final int BLOCK_SIZE = 200;

    private static final int BLOCK_TYPE_COUNT = 3;
    private int posX;
    private int posY;

    public PuzzleBlock(Context context) {
        super(context);
        init();
    }

    public PuzzleBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PuzzleBlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.blockType = (int) (Math.random() * BLOCK_TYPE_COUNT);
        this.setImageResource(imageResourceIds[blockType]);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(BLOCK_SIZE, BLOCK_SIZE);
        this.setLayoutParams(params);
        this.setScaleType(ScaleType.CENTER_CROP);
        this.setPadding(0, 0, 0, 0);
        this.setAdjustViewBounds(true);

        this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
    }


    public void setOnBlockClickListener(OnClickListener listener) {
        this.setOnClickListener(listener);
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
        if (blockType != -1) {
            this.setImageResource(imageResourceIds[blockType]);
        }
    }

    public void setPosition(int x, int y) {
        this.posX = (int) x;
        this.posY = (int) y;
    }

    public int getPositionX() {
        return posX;
    }

    public int getPositionY() {
        return posY;
    }

    public void printInfo() {
        System.out.println("posX: " + posX + ", posY: " + posY + ", blockType: " + blockType);
    }
}