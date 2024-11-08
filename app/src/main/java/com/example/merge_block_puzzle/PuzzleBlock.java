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
    private static final int BLOCK_SIZE = 300;

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

        // layout_widthとlayout_heightをwrap_contentに設定
        this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        // this.setOnClickListener(v -> {
        //     Toast.makeText(getContext(), "PuzzleBlockがクリックされました", Toast.LENGTH_SHORT).show();
        //     // ここにパズルのロジックを追加
        // });

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
        // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams();
        // params.leftMargin = (int) x;
        // params.topMargin = (int) y;
        // this.setLayoutParams(params);
        this.posX = (int) x;
        this.posY = (int) y;
    }

    public int getPositionX() {
        // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams();
        // // java.lang.ClassCastException: android.widget.GridLayout$LayoutParams cannot be cast to android.widget.RelativeLayout$LayoutParams
        // return params.leftMargin;
        // return (int) params.leftMargin;
        return posX;
    }

    public int getPositionY() {
        // RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.getLayoutParams();
        // // return params.topMargin;
        // return (int) params.topMargin;
        return posY;
    }

    public void printInfo() {
        System.out.println("posX: " + posX + ", posY: " + posY + ", blockType: " + blockType);
    }
}