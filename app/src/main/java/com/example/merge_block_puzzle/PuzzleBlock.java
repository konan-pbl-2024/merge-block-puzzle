package com.example.merge_block_puzzle;

import android.content.Context;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatImageButton;

public class PuzzleBlock extends AppCompatImageButton {
    private int blockType;
    private int positionX;
    private int positionY;

    public PuzzleBlock(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 初期のブロックタイプをランダムに設定（0～2）
        setBlockType((int) (Math.random() * 3));
    }

    public void setBlockType(int type) {
        this.blockType = type;
        updateAppearance();
    }

    public int getBlockType() {
        return blockType;
    }

    private void updateAppearance() {
        // ブロックタイプに応じて画像を設定
        switch (blockType) {
            case 0:
                setImageResource(R.drawable.block1); // 赤色のブロック画像
                break;
            case 1:
                setImageResource(R.drawable.block4); // 緑色のブロック画像
                break;
            case 2:
                setImageResource(R.drawable.blueblock); // 青色のブロック画像
                break;
            case 3:
                setImageResource(R.drawable.kiiro); // 黄色のブロック画像
                break;
            case 4:
                setImageResource(R.drawable.murasaki2); // 紫色のブロック画像
                break;
            case 5:
                setImageResource(R.drawable.orenge); // 茶色のブロック画像
                break;
            default:
                setImageDrawable(null); // 透明（またはデフォルトの画像を設定）
                break;
        }
    }

    public void setPosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setOnBlockClickListener(OnClickListener listener) {
        this.setOnClickListener(listener);
    }
}
