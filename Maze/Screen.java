package Maze;
//draws balls and sets screen
public class Screen {
	
	
public int[][] pix;

	public Screen() {
		pix = new int[1200][700];
	}
	
	public void clear(int color) {
		for(int i =0; i< 1200; i++) {
			for (int j=0; j <700; j++) {
				pix[i][j]= color;
			}
		}
	}
	
	public void drawBall(int x, int y, int color, int r) {
		for(int i =0; i< 2*r; i++){
			for( int j=0; j<2*r; j++){
				if((i-r)*(i-r)+(j-r)*(j-r) < r*r && (0<x+i) && (x+i<1200) && (0<y+j) && (y+j<700)) {
					pix[(x+i)][(y+j)] = color;
				}
			}
 		}
	}
}
