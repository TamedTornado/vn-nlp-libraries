/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 *  Vietnam National University, Hanoi, Vietnam.
 */
package vn.hus.nlp.fsm.test;

import vn.hus.nlp.fsm.IConstants;
import vn.hus.nlp.fsm.builder.FSMBuilder;
import vn.hus.nlp.fsm.builder.SimpleFSMBuilder;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com
 * <p>
 * Created: Jan 29, 2008, 4:15:21 PM
 * <p>
 * Test the construction of a simple FST.
 */
public class SimpleFSTClient {
	
	
	public static void test1() {
		String[] inputs = {"ba"};
		
//		String[][] outputs = {{"b", "a"}};
//		String[][] outputs = {{"m", "e"}};
		String[][] outputs = {{"ong", "ba"}};
		
		FSMBuilder  builder = new SimpleFSMBuilder(IConstants.FSM_FST);
		builder.create(inputs, outputs);
		builder.printMachine();
		builder.dispose();
	}
	
	public static void test2() {
		String[] inputs = {"ba", "bo"};
		
//		String[][] outputs = {{"b", "a"}, {"b", "o"}};
		String[][] outputs = {{"b", "a"}, {"c", "o"}};
		
		FSMBuilder  builder = new SimpleFSMBuilder(IConstants.FSM_FST);
		builder.create(inputs, outputs);
		builder.printMachine();
		builder.dispose();
	}

	
	
	public static void main(String[] args) {
//		test1();
		test2();
	}
}
