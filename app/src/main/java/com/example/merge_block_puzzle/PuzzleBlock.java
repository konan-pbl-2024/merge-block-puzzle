package com.example.merge_block_puzzle;

import android.content.Context;
import androidx.appcompat.widget.AppCompatButton;

public class PuzzleBlock extends AppCompatButton {
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
        // ブロックタイプに応じて外観を変更
        switch (blockType) {
            case 0:
                setBackgroundColor(0xFFE57373); // 赤
                break;
            case 1:
                setBackgroundColor(0xFF81C784); // 緑
                break;
            case 2:
                setBackgroundColor(0xFF64B5F6); // 青
                break;
            case 3:
                setBackgroundColor(0xFFFFD54F); // 黄色
                break;
            case 4:
                setBackgroundColor(0xFFBA68C8); // 紫
                break;
            case 5:
                setBackgroundColor(0xFFA1887F); // 茶色
                break;
            default:
                setBackgroundColor(0x00000000); // 透明
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
