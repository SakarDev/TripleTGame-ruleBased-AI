import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TripleTgame {

	static ArrayList<Integer> p1positions = new ArrayList<Integer>();
	static ArrayList<Integer> p2positions = new ArrayList<Integer>();
	static List<Integer> p1placePoints = new ArrayList<Integer>();
	static List<Integer> p2placePoints = new ArrayList<Integer>();
	static List<Integer> row = new ArrayList<Integer>();
	static List<Integer> col = new ArrayList<Integer>();
	static List<Integer> Rcross = new ArrayList<Integer>();
	static List<Integer> Lcross = new ArrayList<Integer>();
	static List<Integer> rowPC = new ArrayList<Integer>();
	static List<Integer> colPC = new ArrayList<Integer>();
	static List<Integer> RcrossPC = new ArrayList<Integer>();
	static List<Integer> LcrossPC = new ArrayList<Integer>();
	static int size = 0;
	static String plr1 = "";
	static String plr2 = "";
	static Scanner sc = new Scanner(System.in);
	static Random rn = new Random();
	static int pcMove = 0;
	static int p1number = 0;
	static int p2number = 0;
	static int P1point = 0;
	static int P2point = 0;

	public static void main(String[] args) {
		try {
			if (who_to_playWith().equals("Player")) {
				getInformation_playBetweenPlayers();
				gameBoard_GetMove_2players();
			} else {
				getInfomration_playWithPc();
				gameBoard_GetMove_withPc();
			}
		}catch (Exception e) {
			//e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Something went wrong! Game ended.");
		}
	}
	public static String who_to_playWith() {
		String q = "Choose a player:";
		String[] ans = { "Computer", "Player" };
		ImageIcon triple_icon = new ImageIcon("src\\triple.png");
		String play_with = (String) JOptionPane.showInputDialog(null, q, "Triple T Game", 1, triple_icon, ans, ans[0]);
		return play_with;
	}


	public static void getInformation_playBetweenPlayers() {
		plr1 = JOptionPane.showInputDialog("Enter 1st Player name : ");
		plr1.trim().toLowerCase();
		if (plr1.isBlank()) {
			plr1 = "Player 1";
		}
		plr1 = plr1.toUpperCase().charAt(0) + plr1.substring(1);
		plr2 = JOptionPane.showInputDialog("Enter 2nd player name : ");
		if (plr2.isBlank()) {
			plr2 = "Player 2";
		}
		plr2.trim().toLowerCase();
		plr2 = plr2.toUpperCase().charAt(0) + plr2.substring(1);
		while (plr1.equalsIgnoreCase(plr2)) {
			JOptionPane.showMessageDialog(null, "Same name! Write a diffrent one.");
			plr2 = JOptionPane.showInputDialog("2nd player name : ");
			plr2.trim().toLowerCase();
			plr2 = plr2.toUpperCase().charAt(0) + plr2.toLowerCase().substring(1);
			if (plr2.isBlank()) {
				plr2 = "Player 2";
			}
		}
		size = Integer.parseInt(JOptionPane.showInputDialog("Enter the size of the Game board (3-9) : "));
		while (size <= 2 || size > 9) {
			size = Integer.parseInt(JOptionPane.showInputDialog("Please choose a size form 3 to 9! "));
		}
		int dice1 = 0;
		int dice2 = 0;
		while (dice1 == dice2) {
			System.out.println(plr1 + ", Press \"Enter\" to roll your dice:");
			sc.nextLine();
			dice1 = rn.nextInt(6) + 1;
			System.out.println(plr2 + ", Press \"Enter\" to roll your dice:");
			sc.nextLine();
			dice2 = rn.nextInt(6) + 1;
			if (dice1 == dice2) {
				System.out.println("dices were equal, another turn : \n");
			}
		}
		System.out.println(plr1 + " rolls a " + dice1);
		System.out.println(plr2 + " rolls a " + dice2);
		System.out.println();
		String str1 = "";
		if (dice2 > dice1) {
			str1 = plr1;
			plr1 = plr2;
			plr2 = str1;
		}
		System.out.println("First turn is yours " + plr1);
	}
	public static void gameBoard_GetMove_2players() {
		int len = size * 2 + 1;
		//length = 11,     index = 0 to 10
		int pos = 1;
		String gameBoard[][] = new String[len][len];
		for (int i = 0; i < len; i += 2) {
			for (int j = 0; j < len; j += 2) {
				//len - 1 = 10   j <10
				if (j < len - 1) {
					gameBoard[i][j] = "+--";
				}else {
					gameBoard[i][j] = "+";
				}
				if (j + 1 < len) {
					gameBoard[i][j + 1] = "---";
				}
				if (i + 1 < len) {
					gameBoard[i + 1][j] = "|";
					String posit = pos + "";
					if (j + 1 < len) {
						gameBoard[i + 1][j + 1] = posit;
						pos++;
					}
				}
			}
		}
		printGameBoard(gameBoard);
		System.out.println("This symobl X is yours " + plr1);
		System.out.println("This symobl O is yours " + plr2);
		System.out.println();
		while (true) {
			if (noAnyMorePositionRemain()) {
				System.out.println(findWinner(gameBoard));
				break;
			}
			p1Move();
			replacePos(p1number, plr1, gameBoard);
			if (noAnyMorePositionRemain()) {
				System.out.println(findWinner(gameBoard));
				break;
			}
			p2Move();
			replacePos(p2number, plr2, gameBoard);
			System.out.println();
		}
	}


	public static void printGameBoard(String gameBoard[][]) {
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				System.out.printf("%-3s", gameBoard[i][j]);
			}
			System.out.println();
		}
	}
	public static boolean noAnyMorePositionRemain() {
		if (size % 2 == 0) {
			if (size * size / 2 == p1positions.size() && size * size / 2 == p2positions.size()) {
				return true;
			}
		} else if (size % 2 == 1) {		
			if (size * size / 2 + 1 == p1positions.size() && size * size / 2 == p2positions.size()) {
				return true;
			}
		}
		return false;
	}
	public static void replacePos(int position, String name, String array[][]) {
		String symobl = "";
		if (name.equals(plr1)) {
			p1positions.add(position);
			row.clear();//Only in single player mode we need these 4 Lists
			col.clear();
			Rcross.clear();
			Lcross.clear();
			row.addAll(p1positions);
			col.addAll(p1positions);
			Rcross.addAll(p1positions);
			Lcross.addAll(p1positions);
			symobl = "X";
		} else {
			p2positions.add(position);
			rowPC.clear();//Only in single player mode we need these 4 Lists
			colPC.clear();
			RcrossPC.clear();
			LcrossPC.clear();
			rowPC.addAll(p2positions);
			colPC.addAll(p2positions);
			RcrossPC.addAll(p2positions);
			LcrossPC.addAll(p2positions);
			symobl = "O";
		}
		for (int i = 1; i < array.length; i += 2) {
			for (int j = 1; j < array[i].length; j += 2) {
				if (array[i][j].equals(String.valueOf(position))) {
					array[i][j] = symobl;
				}
			}
		}
		whatToDelete();
		whatToDeletePC();
		printGameBoard(array);
	}


	public static List<Integer> wrongRightCrossNumbers() {
		List<Integer> wrongRightCrossNumbers = new ArrayList<Integer>();
		for (int x = 0; x < size; x++) {
			wrongRightCrossNumbers.add(x * size + 1);
			wrongRightCrossNumbers.add(x * size + 2);
		}
		return wrongRightCrossNumbers;
	}
	public static List<Integer> wrongStartRow_LeftCrossNumbers() {
		List<Integer> wrongStartRow_LeftCrossNumbers = new ArrayList<Integer>();
		for (int x = 1; x <= size; x++) {
			wrongStartRow_LeftCrossNumbers.add(size * x);
			wrongStartRow_LeftCrossNumbers.add(size * x - 1);
		}
		return wrongStartRow_LeftCrossNumbers;
	}


	public static void p1Move() {
		System.out.println(plr1 + " enter a number : ");
		p1number = sc.nextInt();
		while (p1positions.contains(p1number) || p2positions.contains(p1number) || 
				p1number <= 0 || p1number > size * size) {
			System.out.println(plr1 + ", Position isn't available! Try another one : ");
			p1number = sc.nextInt();
		}
	}
	public static void p2Move() {
		System.out.println();
		System.out.println(plr2 + " enter a number : ");
		p2number = sc.nextInt();
		while (p1positions.contains(p2number) || p2positions.contains(p2number) || 
				p2number <= 0 || p2number > size * size) {
			System.out.println(plr2 + ", Position isn't available! Try another one : ");
			p2number = sc.nextInt();
		}
	}
	public static String findWinner(String array[][]) {
		System.out.println();
		player1Points();
		player2Points();
		System.out.println(plr1 + " points : " + P1point);
		System.out.println(plr2 + " points : " + P2point);
		System.out.println("\n");

//		System.out.println(plr1 + " positions after sort " + p1positions);
//		System.out.println(plr2 + " positions after sort " + p2positions);
//		System.out.println();
//		System.out.println(plr1 + "  p1placePoints " + p1placePoints);
//		System.out.println(plr2 + "  p2placePoints " + p2placePoints);

		if (P1point > P2point) {
			return plr1 + " wins!";
		} else if (P1point < P2point) {
			return plr2 + " wins!";
		} else {
			return "Draw!";
		}
	}
	public static void player1Points() {
		Collections.sort(p1positions);
		System.out.println(plr1 + " positions : " + p1positions);
		System.out.println(plr2 + " positions : " + p2positions);
		List<Integer> p1row = new ArrayList<Integer>(p1positions);
		List<Integer> p1col = new ArrayList<Integer>(p1positions);
		List<Integer> p1rightCross = new ArrayList<Integer>(p1positions);
		List<Integer> p1leftCross = new ArrayList<Integer>(p1positions);
		// Row player 1
		for (int i = 0; i < p1row.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(p1row.get(i))) {
				continue;
			}
			if (p1row.contains(p1row.get(i)) && p1row.contains(p1row.get(i) + 1) && p1row.contains(p1row.get(i) + 2)) {
				p1placePoints.add(p1row.get(i));
				p1placePoints.add(p1row.get(i) + 1);
				p1placePoints.add(p1row.get(i) + 2);
				P1point++;
				p1row.remove(p1row.indexOf(p1row.get(i) + 1));
				p1row.remove(p1row.indexOf(p1row.get(i) + 2));
				p1row.remove(p1row.indexOf(p1row.get(i)));
				i--;
			}
		}
		// column player 1
		for (int i = 0; i < p1col.size(); i++) {
			if (p1col.contains(p1col.get(i)) && p1col.contains(p1col.get(i) + size)
					&& p1col.contains(p1col.get(i) + (2 * size))) {
				p1placePoints.add(p1col.get(i));
				p1placePoints.add(p1col.get(i) + size);
				p1placePoints.add(p1col.get(i) + (2 * size));
				P1point++;
				p1col.remove(p1col.indexOf(p1col.get(i) + size));
				p1col.remove(p1col.indexOf(p1col.get(i) + (2 * size)));
				p1col.remove(p1col.indexOf(p1col.get(i)));
				i--;
			}
		}
		// Right Cross Player 1
		for (int i = 0; i < p1rightCross.size(); i++) {
			if (wrongRightCrossNumbers().contains(p1rightCross.get(i))) {
				continue;
			}
			if (p1rightCross.contains(p1rightCross.get(i)) && p1rightCross.contains(p1rightCross.get(i) + (size - 1))
					&& p1rightCross.contains(p1rightCross.get(i) + 2 * (size - 1))) {
				p1placePoints.add(p1rightCross.get(i));
				p1placePoints.add(p1rightCross.get(i) + (size - 1));
				p1placePoints.add(p1rightCross.get(i) + 2 * (size - 1));
				P1point++;
				p1rightCross.remove(p1rightCross.indexOf(p1rightCross.get(i) + (size - 1)));
				p1rightCross.remove(p1rightCross.indexOf(p1rightCross.get(i) + 2 * (size - 1)));
				p1rightCross.remove(p1rightCross.get(i));
				i--;
			}
		}
		// Left Cross player 1
		for (int i = 0; i < p1leftCross.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(p1leftCross.get(i))) {
				continue;
			}
			if (p1leftCross.contains(p1leftCross.get(i)) && p1leftCross.contains(p1leftCross.get(i) + (size + 1))
					&& p1leftCross.contains(p1leftCross.get(i) + 2 * (size + 1))) {
				p1placePoints.add(p1leftCross.get(i));
				p1placePoints.add(p1leftCross.get(i) + (size + 1));
				p1placePoints.add(p1leftCross.get(i) + 2 * (size + 1));
				P1point++;
				p1leftCross.remove(p1leftCross.indexOf(p1leftCross.get(i) + (size + 1)));
				p1leftCross.remove(p1leftCross.indexOf(p1leftCross.get(i) + 2 * (size + 1)));
				p1leftCross.remove(p1leftCross.get(i));
				i--;
			}
		}
	}
	public static void player2Points() {
		Collections.sort(p2positions);
		List<Integer> p2row = new ArrayList<Integer>(p2positions);
		List<Integer> p2col = new ArrayList<Integer>(p2positions);
		List<Integer> p2riCross = new ArrayList<Integer>(p2positions);
		List<Integer> p2lefCross = new ArrayList<Integer>(p2positions);
		// Row player 2
		for (int i = 0; i < p2row.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(p2row.get(i))) {
				continue;
			}
			if (p2row.contains(p2row.get(i)) && p2row.contains(p2row.get(i) + 1) && p2row.contains(p2row.get(i) + 2)) {
				p2placePoints.add(p2row.get(i));
				p2placePoints.add(p2row.get(i) + 1);
				p2placePoints.add(p2row.get(i) + 2);
				P2point++;
				p2row.remove(p2row.indexOf(p2row.get(i) + 1));
				p2row.remove(p2row.indexOf(p2row.get(i) + 2));
				p2row.remove(p2row.indexOf(p2row.get(i)));
				i--;
			}
		}
		// column player 2
		for (int i = 0; i < p2col.size(); i++) {
			if (p2col.contains(p2col.get(i)) && p2col.contains(p2col.get(i) + size)
					&& p2col.contains(p2col.get(i) + (2 * size))) {
				p2placePoints.add(p2col.get(i));
				p2placePoints.add(p2col.get(i) + size);
				p2placePoints.add(p2col.get(i) + (2 * size));
				P2point++;
				p2col.remove(p2col.indexOf(p2col.get(i) + size));
				p2col.remove(p2col.indexOf(p2col.get(i) + (2 * size)));
				p2col.remove(p2col.indexOf(p2col.get(i)));
				i--;
			}
		}
		// Right Cross Player 2
		for (int i = 0; i < p2riCross.size(); i++) {
			if (wrongRightCrossNumbers().contains(p2riCross.get(i))) {
				continue;
			}
			if (p2riCross.contains(p2riCross.get(i)) && p2riCross.contains(p2riCross.get(i) + (size - 1))
					&& p2riCross.contains(p2riCross.get(i) + 2 * (size - 1))) {
				p2placePoints.add(p2riCross.get(i));
				p2placePoints.add(p2riCross.get(i) + (size - 1));
				p2placePoints.add(p2riCross.get(i) + 2 * (size - 1));
				P2point++;
				p2riCross.remove(p2riCross.indexOf(p2riCross.get(i) + (size - 1)));
				p2riCross.remove(p2riCross.indexOf(p2riCross.get(i) + 2 * (size - 1)));
				p2riCross.remove(p2riCross.get(i));
				i--;
			}
		}
		// Left cross player 2
		for (int i = 0; i < p2lefCross.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(p2lefCross.get(i))) {
				continue;
			}
			if (p2lefCross.contains(p2lefCross.get(i)) && p2lefCross.contains(p2lefCross.get(i) + (size + 1))
					&& p2lefCross.contains(p2lefCross.get(i) + 2 * (size + 1))) {
				p2placePoints.add(p2lefCross.get(i));
				p2placePoints.add(p2lefCross.get(i) + (size + 1));
				p2placePoints.add(p2lefCross.get(i) + 2 * (size + 1));
				P2point++;
				p2lefCross.remove(p2lefCross.indexOf(p2lefCross.get(i) + (size + 1)));
				p2lefCross.remove(p2lefCross.indexOf(p2lefCross.get(i) + 2 * (size + 1)));
				p2lefCross.remove(p2lefCross.get(i));
				i--;
			}
		}

	}


	public static void getInfomration_playWithPc() {
		plr1 = JOptionPane.showInputDialog("Enter your name: ");
		if (plr1.isBlank()) {
			plr1 = "Player";
		}
		plr1.trim().toLowerCase();
		plr1 = plr1.toUpperCase().charAt(0) + plr1.substring(1);
		size = Integer.parseInt(JOptionPane.showInputDialog("Enter the size of the Game board (3-9) :"));
		while (size <= 2 || size > 9) {
			size = Integer.parseInt(JOptionPane.showInputDialog("Please choose a size from 3 to 9!"));
		}
		plr2 = "Computer";
	}
	public static void gameBoard_GetMove_withPc() {
		int len = size * 2 + 1;
		int pos = 1;
		String gameBoard[][] = new String[len][len];
		for (int i = 0; i < len; i += 2) {
			for (int j = 0; j < len; j += 2) {
				if (j < len - 1) {
					gameBoard[i][j] = "+--";
				} else {
					gameBoard[i][j] = "+";
				}
				if (j + 1 < len) {
					gameBoard[i][j + 1] = "---";
				}
				if (i + 1 < len) {
					gameBoard[i + 1][j] = "|";
					String posit = pos + "";
					if (j + 1 < len) {
						gameBoard[i + 1][j + 1] = posit;
						pos++;
					}
				}
			}
		}
		printGameBoard(gameBoard);
		System.out.println("The owner of this symobl X is " + plr1);
		System.out.println("The owner of this symobl O is " + plr2);
		System.out.println();
		while (true) {
			if (noAnyMorePositionRemain()) {
				System.out.println(findWinner(gameBoard));
				break;
			}
			p1Move();
			replacePos(p1number, plr1, gameBoard);
			if (noAnyMorePositionRemain()) {
				System.out.println(findWinner(gameBoard));
				break;
			}
			pcMoves();
			replacePos(pcMove, plr2, gameBoard);
			System.out.println();
		}
	}
	public static void pcMoves() {
		System.out.println("enter a number PC:");
		pcMove = winningMoves();
		while (p1positions.contains(pcMove) || p2positions.contains(pcMove) || pcMove <= 0 || pcMove > (size * size) ) {
			System.out.println(plr2 + ", Wrong position! try another one : ");
			randomlyPositions();
		}
		System.out.println(pcMove);
	}
	public static int winningMoves() {
		// Row PC ***
		for (int i = 0; i < rowPC.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(rowPC.get(i))) {
				continue;
			}
			if (rowPC.contains(rowPC.get(i)) && rowPC.contains(rowPC.get(i) + 1)) {										//**-
				if (!p1positions.contains(rowPC.get(i) + 2) && !p2positions.contains(rowPC.get(i) + 2)
						&& rowPC.get(i) + 2 <= size * size) {
					System.out.println("winningMoves() 1");
					return pcMove = rowPC.get(i) + 2;
				}
			}
			if (rowPC.contains(rowPC.get(i)) && rowPC.contains(rowPC.get(i) + 2)) {										// *-*
				if (!p1positions.contains(rowPC.get(i) + 1) && !p2positions.contains(rowPC.get(i) + 1)) {
					System.out.println("winningMoves() 2");
					return pcMove = rowPC.get(i) + 1;
				}
			}
		}
		for (int i = 0; i < rowPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(rowPC.get(i))) {
				continue;	
			}
			if (rowPC.contains(rowPC.get(i)) && rowPC.contains(rowPC.get(i) - 1)) {										// -**
				if (!p1positions.contains(rowPC.get(i) - 2) && !p2positions.contains(rowPC.get(i) - 2)
						&& rowPC.get(i) - 2 > 0 ) {
					System.out.println("winningMoves() 3");
					return pcMove = rowPC.get(i) - 2;
				}
			}
		}
		// Column PC
		for (int i = 0; i < colPC.size(); i++) {
			if (colPC.contains(colPC.get(i)) && colPC.contains(colPC.get(i) + size)) {										// *
				if (!p1positions.contains(colPC.get(i) + (2 * size)) && !p2positions.contains(colPC.get(i) + (2 * size))	// *
						&& colPC.get(i) + (2 * size) <= size * size) {														// -
					System.out.println("winningMoves() 4");
					return pcMove = colPC.get(i) + (2 * size);
				}
				if (!p1positions.contains(colPC.get(i) - size) && !p2positions.contains(colPC.get(i) - size)				// -
						&& colPC.get(i) - size > 0 ) {																		// *
					System.out.println("winningMoves() 6");																	// *
					return pcMove = colPC.get(i) - size;
				}
			}
			if (colPC.contains(colPC.get(i)) && colPC.contains(colPC.get(i) + (2 * size))) {								// *
				if (!p1positions.contains(colPC.get(i) + size) && !p2positions.contains(colPC.get(i) + size)				// -
						&& colPC.get(i) + size <= size * size) {															// *
					System.out.println("winningMoves() 5");
					return pcMove = colPC.get(i) + size;
				}
			}
		}
		// Right Cross PC
		for (int i = 0; i < RcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(RcrossPC.get(i))) {
				continue;
			}
			if (RcrossPC.contains(RcrossPC.get(i)) && RcrossPC.contains(RcrossPC.get(i) + size - 1)) {	
				if (!p1positions.contains(RcrossPC.get(i) + 2 * (size - 1))															// *
						&& !p2positions.contains(RcrossPC.get(i) + 2 * (size - 1))												// *
						&& RcrossPC.get(i) + 2 * (size - 1) <= size * size) {												// -
					System.out.println("winningMoves() 7");
					return pcMove = RcrossPC.get(i) + 2 * (size - 1);
				}
			}
			if (RcrossPC.contains(RcrossPC.get(i)) && RcrossPC.contains(RcrossPC.get(i) + 2 * (size - 1))) {						// *
				if (!p1positions.contains(RcrossPC.get(i) + (size - 1))															// -
						&& !p2positions.contains(RcrossPC.get(i) + (size - 1))) {											// *
					System.out.println("winningMoves() 8");
					return pcMove = RcrossPC.get(i) + (size - 1);
				}
			}
		}
		for (int i = 0; i < RcrossPC.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(RcrossPC.get(i)) ) {
				continue;
			}
			if (RcrossPC.contains(RcrossPC.get(i)) && RcrossPC.contains(RcrossPC.get(i) - (size - 1))) {							// -
				if (!p1positions.contains(RcrossPC.get(i) - 2 * (size - 1))														// *
						&& !p2positions.contains(RcrossPC.get(i) - 2 * (size - 1))											// *
						&& RcrossPC.get(i) - 2 * (size - 1) > 0) {
					System.out.println("winningMoves() 9");
					return pcMove = RcrossPC.get(i) - 2 * (size - 1);
				}
			}
		}
		// Left Cross PC
		for (int i = 0; i < LcrossPC.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(LcrossPC.get(i))) {
				continue;
			}
			if (LcrossPC.contains(LcrossPC.get(i)) && LcrossPC.contains(LcrossPC.get(i) + size + 1)) {					// *
				if (!p1positions.contains(LcrossPC.get(i) + 2 * (size + 1))													// *
						&& !p2positions.contains(LcrossPC.get(i) + 2 * (size + 1))												// -
						&& LcrossPC.get(i) + 2 * (size + 1) <= size * size) {
					System.out.println("winningMoves() 6");
					return pcMove = LcrossPC.get(i) + 2 * (size + 1);
				}
			}
			if (LcrossPC.contains(LcrossPC.get(i)) && LcrossPC.contains(LcrossPC.get(i) + 2 * (size + 1))) {			// *
				if (!p1positions.contains(LcrossPC.get(i) + (size + 1))														// -
						&& !p2positions.contains(LcrossPC.get(i) + (size + 1)) ) {												// *
					System.out.println("winningMoves() 7");
					return pcMove = LcrossPC.get(i) + (size + 1);
				}
			}
		} 
		for (int i = 0; i < LcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(LcrossPC.get(i)) || LcrossPC.get(i) <= (2 * size)) {
				continue;   
			}
			if (LcrossPC.contains(LcrossPC.get(i)) && LcrossPC.contains(LcrossPC.get(i) - (size + 1))) {				// -
				if (!p1positions.contains(LcrossPC.get(i) - 2 * (size + 1))													// *
						&& !p2positions.contains(LcrossPC.get(i) - 2 * (size + 1))												// *
						&& LcrossPC.get(i) - 2 * (size + 1) > 0) {
					System.out.println("winningMoves() 8");
					return pcMove = LcrossPC.get(i) - 2 * (size + 1);
				}
			}
		}
		return playerGet1Point();
	}
	public static int playerGet1Point() {
		// Row
		for (int i = 0; i < row.size(); i++) {
			if (wrongRightCrossNumbers().contains(row.get(i))) {
				continue;
			}																											// -**
			if (row.contains(row.get(i)) && row.contains(row.get(i) - 1)) {
				if (!p1positions.contains(row.get(i) - 2) && !p2positions.contains(row.get(i) - 2) && row.get(i) - 2 > 0
						&& row.get(i) - 2 <= size * size) {
					System.out.println("playerGet1Point() 1");
					return pcMove = row.get(i) - 2;
				}
			}
		}
		for (int i = 0; i < row.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(row.get(i))) {
				continue;
			}																											// **-
			if (row.contains(row.get(i)) && row.contains(row.get(i) + 1)) {
				if (!p1positions.contains(row.get(i) + 2) && !p2positions.contains(row.get(i) + 2) && row.get(i) + 2 > 0
						&& row.get(i) + 2 <= size * size) {
					System.out.println("playerGet1Point() 2");
					return pcMove = row.get(i) + 2;
				}
			}																											// *-*
			if (row.contains(row.get(i)) && row.contains(row.get(i) + 2)) {
				if (!p1positions.contains(row.get(i) + 1) && !p2positions.contains(row.get(i) + 1) && row.get(i) + 1 > 0
						&& row.get(i) + 1 <= size * size) {
					System.out.println("playerGet1Point() 3");
					return pcMove = row.get(i) + 1;
				}
			}
		}
		// Column
		for (int i = 0; i < col.size(); i++) {
			if (col.contains(col.get(i)) && col.contains(col.get(i) + size)) {											
				if (!p1positions.contains(col.get(i) + (2 * size)) && !p2positions.contains(col.get(i) + (2 * size))	// *
						&& col.get(i) + (2 * size) <= size * size) {													// *
					System.out.println("playerGet1Point() 4");															// -
					return pcMove = col.get(i) + (2 * size);
				}
				if (!p1positions.contains(col.get(i) - size) && !p2positions.contains(col.get(i) - size)				// -
						&& col.get(i) - size > 0 ) {																	// *
					System.out.println("playerGet1Point() 6");															// *
					return pcMove = col.get(i) - size;
				}
			}
			if (col.contains(col.get(i)) && col.contains(col.get(i) + (2 * size))) {									// *
				if (!p1positions.contains(col.get(i) + size) && !p2positions.contains(col.get(i) + size)) {				// -					
					System.out.println("playerGet1Point() 5");															// *
					return pcMove = col.get(i) + size;
				}
			}
		}
		// left cross
		for (int i = 0; i < Lcross.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(Lcross.get(i))) {
				continue;
			}
			if (Lcross.contains(Lcross.get(i)) && Lcross.contains(Lcross.get(i) + size + 1)) {							// *
				if (!p1positions.contains(Lcross.get(i) + 2 * (size + 1))													// *
						&& !p2positions.contains(Lcross.get(i) + 2 * (size + 1)) 												// -
						&& Lcross.get(i) + 2 * (size + 1) <= size * size) {													
					System.out.println("playerGet1Point() 7");
					return pcMove = Lcross.get(i) + 2 * (size + 1);
				}
			}
			if (Lcross.contains(Lcross.get(i)) && Lcross.contains(Lcross.get(i) + 2 * (size + 1))) {					// *
				if (!p1positions.contains(Lcross.get(i) + (size + 1))														// -
						&& !p2positions.contains(Lcross.get(i) + (size + 1)) ) {												// *
					System.out.println("playerGet1Point() 8");
					return pcMove = Lcross.get(i) + (size + 1);
				}
			}
		}
		for (int i = 0; i < Lcross.size(); i++) {
			if (wrongRightCrossNumbers().contains(Lcross.get(i))) {
				continue;
			}
			if (Lcross.contains(Lcross.get(i)) && Lcross.contains(Lcross.get(i) - (size + 1))) {						// -
				if (!p1positions.contains(Lcross.get(i) - 2 * (size + 1))													// *
						&& !p2positions.contains(Lcross.get(i) - 2 * (size + 1)) 												// *
						&& Lcross.get(i) - 2 * (size + 1) > 0) {
					System.out.println("playerGet1Point() 9");
					return pcMove = Lcross.get(i) - 2 * (size + 1);
				}
			}
		}
		// right cross
		for (int i = 0; i < Rcross.size(); i++) {
			if (wrongRightCrossNumbers().contains(Rcross.get(i))) {
				continue;
			}
			if (Rcross.contains(Rcross.get(i)) && Rcross.contains(Rcross.get(i) + size - 1)) {									// *
				if (!p1positions.contains(Rcross.get(i) + 2 * (size - 1))													// *
						&& !p2positions.contains(Rcross.get(i) + 2 * (size - 1))										// -
						&& Rcross.get(i) + 2 * (size - 1) <= size * size) {
					System.out.println("playerGet1Point() 10");
					return pcMove = Rcross.get(i) + 2 * (size - 1);
				}
			}
			if (Rcross.contains(Rcross.get(i)) && Rcross.contains(Rcross.get(i) + 2 * (size - 1))) {							// *
				if (!p1positions.contains(Rcross.get(i) + (size - 1))														// -
						&& !p2positions.contains(Rcross.get(i) + (size - 1))) {											// *
					System.out.println("playerGet1Point() 11");
					return pcMove = Rcross.get(i) + (size - 1);
				}
			}
		}
		for (int i = 0; i < Rcross.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(Rcross.get(i))) {
				continue;
			}
			if (Rcross.contains(Rcross.get(i)) && Rcross.contains(Rcross.get(i) - (size - 1))) {								// -
				if (!p1positions.contains(Rcross.get(i) - 2 * (size - 1))													// *
						&& !p2positions.contains(Rcross.get(i) - 2 * (size - 1)) 										// *
						&& Rcross.get(i) - 2 * (size - 1) > 0) {
					System.out.println("playerGet1Point() 12");
					return pcMove = Rcross.get(i) - 2 * (size - 1);
				}
			}
		}
		return rowPrepareToWinningMove();
	}
	public static int rowPrepareToWinningMove() {
		// Row PC
		for (int i = 0; i < rowPC.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(rowPC.get(i))) {
				continue;
			}
			if (rowPC.contains(rowPC.get(i))) {
				if (!p1positions.contains(rowPC.get(i) + 1) && !p2positions.contains(rowPC.get(i) + 1)								// *--
						&& !p1positions.contains(rowPC.get(i) + 2) && !p2positions.contains(rowPC.get(i) + 2)
						&& rowPC.get(i) + 2 <= size * size) {
					System.out.println("rowPrepareToWinningMove() 1");
					return pcMove = rowPC.get(i) + 1;
				}
			}
		}
		for (int i = 0; i < rowPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(rowPC.get(i))) {
				continue;
			}
			if (rowPC.contains(rowPC.get(i))) {																						
				if (!p1positions.contains(rowPC.get(i) - 1) && !p2positions.contains(rowPC.get(i) - 1)								// --*
						&& !p1positions.contains(rowPC.get(i) - 2) && !p2positions.contains(rowPC.get(i) - 2)
						&& rowPC.get(i) - 2 > 0 ) {
					System.out.println("rowPrepareToWinningMove() 2");
					return pcMove = rowPC.get(i) - 1;
				}
			}
		}
		for (int i = 0; i < rowPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(rowPC.get(i))
					|| wrongStartRow_LeftCrossNumbers().contains(rowPC.get(i))) {
				continue;
			}
			if (rowPC.contains(rowPC.get(i))) {
				if (!p1positions.contains(rowPC.get(i) - 1) && !p2positions.contains(rowPC.get(i) - 1)								// -*-
						&& !p1positions.contains(rowPC.get(i) + 1) && !p2positions.contains(rowPC.get(i) + 1)
						&& rowPC.get(i) - 1 > 0) {
					System.out.println("rowPrepareToWinningMove() 3");
					return pcMove = rowPC.get(i) - 1;
				}
			}
		}
		for (int i = 0; i < rowPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(rowPC.get(i)) && (rowPC.get(i) - 2) % size == 0) {								// -*-
				if (!p1positions.contains(rowPC.get(i) - 1) && !p2positions.contains(rowPC.get(i) - 1)
						&& !p1positions.contains(rowPC.get(i) + 1) && !p2positions.contains(rowPC.get(i) + 1)) {
					System.out.println("rowPrepareToWinningMove() 4");
					return pcMove = rowPC.get(i) - 1;
				}
			}
			if (wrongStartRow_LeftCrossNumbers().contains(rowPC.get(i)) && rowPC.get(i) % size == 0) {								// --*
				if (!p1positions.contains(rowPC.get(i) - 1) && !p2positions.contains(rowPC.get(i) - 1)
						&& !p1positions.contains(rowPC.get(i) - 2) && !p2positions.contains(rowPC.get(i) - 2)) {
					System.out.println("rowPrepareToWinningMove() 5");
					return pcMove = rowPC.get(i) - 1;
				}
			}
			if (wrongStartRow_LeftCrossNumbers().contains(rowPC.get(i)) && (rowPC.get(i) + 1) % size == 0) {						// -*-
				if (!p1positions.contains(rowPC.get(i) - 1) && !p2positions.contains(rowPC.get(i) - 1)
						&& !p1positions.contains(rowPC.get(i) + 1) && !p2positions.contains(rowPC.get(i) + 1)) {
					System.out.println("rowPrepareToWinningMove() 6");
					return pcMove = rowPC.get(i) - 1;
				}
			}
		}
		return colPrepareToWinningMove();
	}
	public static int colPrepareToWinningMove() {
		// Column PC *
		// *
		for (int i = 0; i < colPC.size(); i++) {
			if (colPC.contains(colPC.get(i))) {
				if (!p1positions.contains(colPC.get(i) + size) && !p2positions.contains(colPC.get(i) + size)						// *
						&& !p1positions.contains(colPC.get(i) + (2 * size))															// -
						&& !p2positions.contains(colPC.get(i) + (2 * size)) 														// -
						&& colPC.get(i) + (2 * size) <= size * size) {
					System.out.println("colPrepareToWinningMove() 1");
					return pcMove = colPC.get(i) + size;
				}
				if (!p1positions.contains(colPC.get(i) - size) && !p2positions.contains(colPC.get(i) - size)						// -
						&& !p1positions.contains(colPC.get(i) - (2 * size))															// -
						&& !p2positions.contains(colPC.get(i) - (2 * size)) && colPC.get(i) - (2 * size) > 0) {						// *
					System.out.println("colPrepareToWinningMove() 2");
					return pcMove = colPC.get(i) - size;
				}
				if (!p1positions.contains(colPC.get(i) - size) && !p2positions.contains(colPC.get(i) - size)						// -
						&& !p1positions.contains(colPC.get(i) + size) && !p2positions.contains(colPC.get(i) + size)					// *
						&& colPC.get(i) + size <= size * size && colPC.get(i) - size > 0) {											// -
					System.out.println("colPrepareToWinningMove() 3");
					return pcMove = colPC.get(i) - size;
				}
			}
		}
		return RcrossPrepareToWinningMove();
	}
	public static int RcrossPrepareToWinningMove() {
		for (int i = 0; i < RcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(RcrossPC.get(i))) {
				continue;
			}
			if (RcrossPC.contains(RcrossPC.get(i))) {
				if (!p1positions.contains(RcrossPC.get(i) + (size - 1))																	// *
						&& !p2positions.contains(RcrossPC.get(i) + (size - 1))														// -
						&& !p1positions.contains(RcrossPC.get(i) + 2 * (size - 1))												// -
						&& !p2positions.contains(RcrossPC.get(i) + 2 * (size - 1))
						&& RcrossPC.get(i) + 2 * (size - 1) > 0 && RcrossPC.get(i) + 2 * (size - 1) <= size * size) {
					System.out.println("RcrossPrepareToWinningMove() 1");
					return pcMove = RcrossPC.get(i) + (size - 1);
				}
			}
		}
		for (int i = 0; i < RcrossPC.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(RcrossPC.get(i)) || RcrossPC.get(i) <= (2 * size)) {
				continue;
			}
			if (RcrossPC.contains(RcrossPC.get(i))) {																						// -
				if (!p1positions.contains(RcrossPC.get(i) - (size - 1))																	// -
						&& !p2positions.contains(RcrossPC.get(i) - (size - 1))														// *
						&& !p1positions.contains(RcrossPC.get(i) - 2 * (size - 1))
						&& !p2positions.contains(RcrossPC.get(i) - 2 * (size - 1))
						&& RcrossPC.get(i) - 2 * (size - 1) > 0 && RcrossPC.get(i) - 2 * (size - 1) <= size * size) {
					System.out.println("RcrossPrepareToWinningMove() 2");
					return pcMove = RcrossPC.get(i) - (size - 1);
				}
			}
		}
		for (int i = 0; i < RcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(RcrossPC.get(i))
					|| wrongStartRow_LeftCrossNumbers().contains(RcrossPC.get(i))) {
				continue;
			}
			if (RcrossPC.contains(RcrossPC.get(i))) {																							// -
				if (!p1positions.contains(RcrossPC.get(i) - (size - 1))																		// *
						&& !p2positions.contains(RcrossPC.get(i) - (size - 1))															// -
						&& !p1positions.contains(RcrossPC.get(i) + (size - 1))
						&& !p2positions.contains(RcrossPC.get(i) + (size - 1)) && RcrossPC.get(i) - (size - 1) > 0
						&& RcrossPC.get(i) - (size - 1) <= size * size && RcrossPC.get(i) - (size - 1) > 0
						&& RcrossPC.get(i) + (size - 1) <= size * size) {
					System.out.println("RcrossPrepareToWinningMove() 3");
					return pcMove = RcrossPC.get(i) - (size + 1);
				}
			}
		}
		for (int i = 0; i < RcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(RcrossPC.get(i)) && (RcrossPC.get(i) - 2) % size == 0) {
				if (!p1positions.contains(RcrossPC.get(i) - (size - 1))																			// -
						&& !p2positions.contains(RcrossPC.get(i) - (size - 1))																// *
						&& !p1positions.contains(RcrossPC.get(i) + (size - 1))															// -
						&& !p2positions.contains(RcrossPC.get(i) + (size - 1)) && RcrossPC.get(i) > size
						&& RcrossPC.get(i) < size * size - size) {
					System.out.println("RcrossPrepareToWinningMove() 4");
					return pcMove = RcrossPC.get(i) - (size - 1);
				}if (!p1positions.contains(RcrossPC.get(i) - (size - 1))																		// -
						&& !p2positions.contains(RcrossPC.get(i) - (size - 1))																// -
						&& !p1positions.contains(RcrossPC.get(i) - 2 * (size - 1))														// *
						&& !p2positions.contains(RcrossPC.get(i) - 2 * (size - 1)) && RcrossPC.get(i) > 2 * size) {
					System.out.println("RcrossPrepareToWinningMove() 5");
					return pcMove = RcrossPC.get(i) - (size - 1);
				}
			}
			if (wrongRightCrossNumbers().contains(RcrossPC.get(i)) && (RcrossPC.get(i) - 1) % size == 0
					&& RcrossPC.get(i) > 2 * size && RcrossPC.get(i) < size * size) {
				if (!p1positions.contains(RcrossPC.get(i) - (size - 1))																			// -
						&& !p2positions.contains(RcrossPC.get(i) - (size - 1))																// -
						&& !p1positions.contains(RcrossPC.get(i) - 2 * (size - 1))														// *
						&& !p2positions.contains(RcrossPC.get(i) - 2 * (size - 1))) {
					System.out.println("RcrossPrepareToWinningMove() 6");
					return pcMove = RcrossPC.get(i) - (size - 1);
				}
			}
			if (wrongStartRow_LeftCrossNumbers().contains(RcrossPC.get(i)) && (RcrossPC.get(i) + 1) % size == 0
					&& RcrossPC.get(i) > size && RcrossPC.get(i) < size * size - size) {
				if (!p1positions.contains(RcrossPC.get(i) - (size - 1))																			// -
						&& !p2positions.contains(RcrossPC.get(i) - (size - 1))																// *
						&& !p1positions.contains(RcrossPC.get(i) + (size - 1))															// -
						&& !p2positions.contains(RcrossPC.get(i) + (size - 1))) {
					System.out.println("RcrossPrepareToWinningMove() 7");
					return pcMove = RcrossPC.get(i) - (size - 1);
				}
			}
		}
		return LcrossPrepareToWinningMove();
	}
	public static int LcrossPrepareToWinningMove() {
		for (int i = 0; i < LcrossPC.size(); i++) {
			if (wrongStartRow_LeftCrossNumbers().contains(LcrossPC.get(i))) {
				continue;
			}
			if (LcrossPC.contains(LcrossPC.get(i))) {
				if (!p1positions.contains(LcrossPC.get(i) + (size + 1)) && !p2positions.contains(LcrossPC.get(i) + (size + 1))
						&& !p1positions.contains(LcrossPC.get(i) + 2 * (size + 1))														// *
						&& !p2positions.contains(LcrossPC.get(i) + 2 * (size + 1))															// -
						&& LcrossPC.get(i) + 2 * (size + 1) <= size * size) {																	// -
					System.out.println("LcrossPrepareToWinningMove() 1");
					return pcMove = LcrossPC.get(i) + (size + 1);
				}
			}
		}
		for (int i = 0; i < LcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(LcrossPC.get(i)) || LcrossPC.get(i) <= (2 * size)) {
				continue;
			}
			if (LcrossPC.contains(LcrossPC.get(i))) {
				if (!p1positions.contains(LcrossPC.get(i) - (size + 1)) && !p2positions.contains(LcrossPC.get(i) - (size + 1))
						&& !p1positions.contains(LcrossPC.get(i) - 2 * (size + 1))														// -
						&& !p2positions.contains(LcrossPC.get(i) - 2 * (size + 1))															// -
						&& LcrossPC.get(i) - 2 * (size + 1) > 0 && LcrossPC.get(i) - 2 * (size + 1) <= size * size) {							// *
					System.out.println("LcrossPrepareToWinningMove() 2");
					return pcMove = LcrossPC.get(i) - (size + 1);
				}
			}
		}
		for (int i = 0; i < LcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(LcrossPC.get(i))
					|| wrongStartRow_LeftCrossNumbers().contains(LcrossPC.get(i))) {
				continue;
			}
			if (LcrossPC.contains(LcrossPC.get(i))) {
				if (!p1positions.contains(LcrossPC.get(i) - (size + 1))																	// -
						&& !p2positions.contains(LcrossPC.get(i) - (size + 1))																// *
						&& !p1positions.contains(LcrossPC.get(i) + (size + 1))																	// -
						&& !p2positions.contains(LcrossPC.get(i) + (size + 1)) && LcrossPC.get(i) - (size + 1) > 0
						&& LcrossPC.get(i) + (size + 1) <= size * size) {
					System.out.println("LcrossPrepareToWinningMove() 3");
					return pcMove = LcrossPC.get(i) - (size + 1);
				}
			}
		}
		for (int i = 0; i < LcrossPC.size(); i++) {
			if (wrongRightCrossNumbers().contains(LcrossPC.get(i)) && (LcrossPC.get(i) - 2) % size == 0
					&& LcrossPC.get(i) > size && LcrossPC.get(i) < size * size - size) {
				if (!p1positions.contains(LcrossPC.get(i) - (size + 1))																	// -
						&& !p2positions.contains(LcrossPC.get(i) - (size + 1))																// *
						&& !p1positions.contains(LcrossPC.get(i) + (size + 1))																	// -
						&& !p2positions.contains(LcrossPC.get(i) + (size + 1))) {
					System.out.println("LcrossPrepareToWinningMove() 4");
					return pcMove = LcrossPC.get(i) - (size + 1);
				}
			}if (wrongStartRow_LeftCrossNumbers().contains(LcrossPC.get(i))
					&& (LcrossPC.get(i) + 1) % size == 0) {																				// -
				if (!p1positions.contains(LcrossPC.get(i) - (size + 1))																		// *
						&& !p2positions.contains(LcrossPC.get(i) - (size + 1))																	// -
						&& !p1positions.contains(LcrossPC.get(i) + (size + 1))
						&& !p2positions.contains(LcrossPC.get(i) + (size + 1)) && LcrossPC.get(i) > size
						&& LcrossPC.get(i) < size * size - size) {
					System.out.println("LcrossPrepareToWinningMove() 5");
					return pcMove = LcrossPC.get(i) - (size + 1);
				}if (!p1positions.contains(LcrossPC.get(i) - (size + 1))
						&& !p2positions.contains(LcrossPC.get(i) - (size + 1))															// -
						&& !p1positions.contains(LcrossPC.get(i) - 2 * (size + 1))															// -
						&& !p2positions.contains(LcrossPC.get(i) - 2 * (size + 1)) && LcrossPC.get(i) > 2 * size) {								// *
					System.out.println("LcrossPrepareToWinningMove() 6");
					return pcMove = LcrossPC.get(i) - (size + 1);
				}
			}if (wrongStartRow_LeftCrossNumbers().contains(LcrossPC.get(i)) && LcrossPC.get(i) % size == 0
					&& LcrossPC.get(i) > 2 * size) {																					// -
				if (!p1positions.contains(LcrossPC.get(i) - (size + 1))																		// -
						&& !p2positions.contains(LcrossPC.get(i) - (size + 1))																	//*
						&& !p1positions.contains(LcrossPC.get(i) - 2 * (size + 1))
						&& !p2positions.contains(LcrossPC.get(i) - 2 * (size + 1))) {
					System.out.println("LcrossPrepareToWinningMove() 7");
					return pcMove = LcrossPC.get(i) - (size + 1);
				}
			}
		}
		return firstMoves();
	}
	public static int firstMoves() {
		if (!p1positions.contains(p1number + 1) && !p2positions.contains(p1number + 1)) {
			if (p1number + 1 > 0 && p1number + 1 <= size * size) {
				System.out.println("firstMoves() 1");
				return pcMove = p1number + 1;
			}
		} else if (!p1positions.contains(p1number + size) && !p2positions.contains(p1number + size)) {
			if (p1number + size > 0 && p1number + size <= size * size) {
				System.out.println("firstMoves() 2");
				return pcMove = p1number + size;
			}
		} else if (!p1positions.contains(p1number + size + 1) && !p2positions.contains(p1number + size + 1)) {
			if (p1number + size + 1 > 0 && p1number + size + 1 <= size * size) {
				System.out.println("firstMoves() 3");
				return pcMove = p1number + size + 1;
			}
		} else if (!p1positions.contains(p1number + size - 1) && !p2positions.contains(p1number + size - 1)) {
			if (p1number + size - 1 > 0 && p1number + size - 1 <= size * size) {
				System.out.println("firstMoves() 4");
				return pcMove = p1number + size - 1;
			}
		}
		return randomlyPositions();
	}
	public static int randomlyPositions() {
		for (int i = 1; i <= size * size; i++) {
			if (!p1positions.contains(i) && !p2positions.contains(i)) {
				System.out.println("randomlyPosition()");
				return pcMove = i;
			}
		}
		return 0;
	}
	public static void whatToDeletePC() {
		Collections.sort(rowPC);
		Collections.sort(colPC);
		Collections.sort(LcrossPC);
		Collections.sort(RcrossPC);
		// Row PC
		for (int i = 0; i < rowPC.size(); i++) {
			while (wrongStartRow_LeftCrossNumbers().contains(rowPC.get(i)) && i + 1 < rowPC.size()) {
				i++;
			}
			if (rowPC.contains(rowPC.get(i)) && rowPC.contains(rowPC.get(i) + 1) && rowPC.contains(rowPC.get(i) + 2)) {
				rowPC.remove(rowPC.indexOf(rowPC.get(i) + 1));
				rowPC.remove(rowPC.indexOf(rowPC.get(i) + 2));
				rowPC.remove(rowPC.indexOf(rowPC.get(i)    ));
				i--;
			}
		}
		// column PC
		for (int i = 0; i < colPC.size(); i++) {
			if (colPC.contains(colPC.get(i)) && colPC.contains(colPC.get(i) + size)
					&& colPC.contains(colPC.get(i) + (2 * size))) {
				colPC.remove(colPC.indexOf(colPC.get(i) + size));
				colPC.remove(colPC.indexOf(colPC.get(i) + (2 * size)));
				colPC.remove(colPC.indexOf(colPC.get(i)));
				i--;
			}
		}
		// Right Cross PC
		for (int i = 0; i < RcrossPC.size(); i++) {
			while (wrongRightCrossNumbers().contains(RcrossPC.get(i)) && i + 1 < RcrossPC.size()) {
				i++;
			}
			if (RcrossPC.contains(RcrossPC.get(i)) && RcrossPC.contains(RcrossPC.get(i) + (size - 1))
					&& RcrossPC.contains(RcrossPC.get(i) + 2 * (size - 1))) {
				RcrossPC.remove(RcrossPC.indexOf(RcrossPC.get(i) + (size - 1)));
				RcrossPC.remove(RcrossPC.indexOf(RcrossPC.get(i) + 2 * (size - 1)));
				RcrossPC.remove(RcrossPC.get(i));
				i--;
			}
		}
		// Left Cross PC
		for (int i = 0; i < LcrossPC.size(); i++) {
			while (wrongStartRow_LeftCrossNumbers().contains(LcrossPC.get(i)) && i + 1 < LcrossPC.size()) {
				i++;
			}
			if (LcrossPC.contains(LcrossPC.get(i)) && LcrossPC.contains(LcrossPC.get(i) + (size + 1))
					&& LcrossPC.contains(LcrossPC.get(i) + 2 * (size + 1))) {
				LcrossPC.remove(LcrossPC.indexOf(LcrossPC.get(i) + (size + 1)));
				LcrossPC.remove(LcrossPC.indexOf(LcrossPC.get(i) + 2 * (size + 1)));
				LcrossPC.remove(LcrossPC.get(i));
				i--;
			}
		}
//		System.out.println(rowPC);
//		System.out.println(colPC);
//		System.out.println(LcrossPC);
//		System.out.println(RcrossPC);
	}
	public static void whatToDelete() {
		Collections.sort(row);
		Collections.sort(col);
		Collections.sort(Lcross);
		Collections.sort(Rcross);
		// Row
		for (int i = 0; i < row.size(); i++) {
			while (wrongStartRow_LeftCrossNumbers().contains(row.get(i)) && i + 1 < row.size()) {
				i++;
			}
			if (row.contains(row.get(i)) && row.contains(row.get(i) + 1) && row.contains(row.get(i) + 2)) {
				row.remove(row.indexOf(row.get(i) + 1));
				row.remove(row.indexOf(row.get(i) + 2));
				row.remove(row.indexOf(row.get(i)));
				i--;
			}
		}
		// column player
		for (int i = 0; i < col.size(); i++) {
			if (col.contains(col.get(i)) && col.contains(col.get(i) + size) && col.contains(col.get(i) + (2 * size))) {
				col.remove(col.indexOf(col.get(i) + size));
				col.remove(col.indexOf(col.get(i) + (2 * size)));
				col.remove(col.indexOf(col.get(i)));
				i--;
			}
		}
		// Right Cross Player
		for (int i = 0; i < Rcross.size(); i++) {
			while (wrongRightCrossNumbers().contains(Rcross.get(i)) && i + 1 < Rcross.size()) {
				i++;
			}
			if (Rcross.contains(Rcross.get(i)) && Rcross.contains(Rcross.get(i) + (size - 1))
					&& Rcross.contains(Rcross.get(i) + 2 * (size - 1))) {
				Rcross.remove(Rcross.indexOf(Rcross.get(i) + (size - 1)));
				Rcross.remove(Rcross.indexOf(Rcross.get(i) + 2 * (size - 1)));
				Rcross.remove(Rcross.get(i));
				i--;
			}
		}
		// Left Cross player
		for (int i = 0; i < Lcross.size(); i++) {
			while (wrongStartRow_LeftCrossNumbers().contains(Lcross.get(i)) && i + 1 < Lcross.size()) {
				i++;
			}
			if (Lcross.contains(Lcross.get(i)) && Lcross.contains(Lcross.get(i) + (size + 1))
					&& Lcross.contains(Lcross.get(i) + 2 * (size + 1))) {
				Lcross.remove(Lcross.indexOf(Lcross.get(i) + (size + 1)));
				Lcross.remove(Lcross.indexOf(Lcross.get(i) + 2 * (size + 1)));
				Lcross.remove(Lcross.get(i));
				i--;
			}
		}
//		System.out.println(row);
//		System.out.println(col);
//		System.out.println(Lcross);
//		System.out.println(Rcross);
	}
	
}
