package MinoBlock;

import java.awt.Color;

import Mino.Block;
import Mino.Mino;

public class Mino3 extends Mino{

	public Mino3() {
		create(Color.orange);
	}
	
	public void setXY(int x, int y) {
		// 
		// 　０     1
		// ０００ 2,0,3
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x;
		b[1].y = b[0].y - Block.SIZE;
		b[2].x = b[0].x - Block.SIZE;
		b[2].y = b[0].y;
		b[3].x = b[0].x + Block.SIZE;
		b[3].y = b[0].y;

	}
	
	public void getDirection1() {
		// 
		// 　０     1
		// ０００ 2,0,3
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - Block.SIZE;
		tempB[2].x = b[0].x - Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x + Block.SIZE;
		tempB[3].y = b[0].y;

		updateXY(1);
	}
	
	public void getDirection2() {
		// ０   2
		// ００ 0,1
		// ０   3  
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x + Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y - Block.SIZE;
		tempB[3].x = b[0].x;
		tempB[3].y = b[0].y + Block.SIZE;

		updateXY(2);
	}
	
	public void getDirection3() {
		//  
		// ０００ 3,0,2
		// 　０     1
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y + Block.SIZE;
		tempB[2].x = b[0].x + Block.SIZE;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x - Block.SIZE;
		tempB[3].y = b[0].y;

		updateXY(3);
	}
	
	public void getDirection4() {
		// 　０   3        
		// ００ 1,0
		// 　０   2
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x - Block.SIZE;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y + Block.SIZE;
		tempB[3].x = b[0].x;
		tempB[3].y = b[0].y - Block.SIZE;

		updateXY(4);
	}
}
