package s4.B203312;  // ここは、かならず、自分の名前に変えよ。
import java.lang.*;
import s4.specification.*;
import java.util.ArrayList; 
import java.util.List;

/*package s4.specification;
  ここは、１回、２回と変更のない外部仕様である。
  public interface FrequencerInterface {     // This interface provides the design for frequency counter.
  void setTarget(byte  target[]); // set the data to search.
  void setSpace(byte  space[]);  // set the data to be searched target from.
  int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
  //Otherwise, it return 0, when SPACE is not set or SPACE's length is zero
  //Otherwise, get the frequency of TAGET in SPACE
  int subByteFrequency(int start, int end);
  // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
  // For the incorrect value of START or END, the behavior is undefined.
  }
*/

public class Frequencer implements FrequencerInterface{
	// Code to start with: This code is not working, but good start point to work.
	byte [] myTarget;
	byte [] mySpace;
	boolean targetReady = false;
	boolean spaceReady = false;

	int []  suffixArray; // Suffix Arrayの実装に使うデータの型をint []とせよ。


	// The variable, "suffixArray" is the sorted array of all suffixes of mySpace.                                    
	// Each suffix is expressed by a integer, which is the starting position in mySpace. 
							
	// The following is the code to print the contents of suffixArray.
	// This code could be used on debugging.                                                                

	// この関数は、デバッグに使ってもよい。mainから実行するときにも使ってよい。
	// リポジトリにpushするときには、mainメッソド以外からは呼ばれないようにせよ。
	//
	private void printSuffixArray() {
		if(spaceReady) {
			for(int i=0; i< mySpace.length; i++) {
				int s = suffixArray[i];
				System.out.printf("suffixArray[%2d]=%2d:", i, s);
				for(int j=s;j<mySpace.length;j++) {
					System.out.write(mySpace[j]);
				}
				System.out.write('\n');
			}
		}
	}

	private int suffixCompare(int i, int j) {
		// suffixCompareはソートのための比較メソッドである。
		// 次のように定義せよ。
		//
		// comparing two suffixes by dictionary order.
		// suffix_i is a string starting with the position i in "byte [] mySpace".
		// When mySpace is "ABCD", suffix_0 is "ABCD", suffix_1 is "BCD", 
		// suffix_2 is "CD", and sufffix_3 is "D".
		// Each i and j denote suffix_i, and suffix_j.                            
		// Example of dictionary order                                            
		// "i"      <  "o"        : compare by code                              
		// "Hi"     <  "Ho"       ; if head is same, compare the next element    
		// "Ho"     <  "Ho "      ; if the prefix is identical, longer string is big  
		//  
		//The return value of "int suffixCompare" is as follows. 
		// if suffix_i > suffix_j, it returns 1
		// if suffix_i < suffix_j, it returns -1
		// if suffix_i = suffix_j, it returns 0;

		// ここにコードを記述せよ 
		// int result = 0;
		// byte[] suffix_i = new byte[mySpace.length-i];
		// byte[] suffix_j = new byte[mySpace.length-j];

		// for(int k=0; k < mySpace.length-i; k++){
		//     suffix_i[k] = mySpace[i+k];
		// }
		// for(int k=0; k < mySpace.length-j; k++){
		//     suffix_j[k] = mySpace[j+k];
		// }
		
		// for(int k=0; ; k++){
		//     if(k < suffix_i.length && k < suffix_j.length){
		//         if(suffix_i[k] != suffix_j[k]){
		//             if(suffix_i[k] > suffix_j[k]){
		//                 result = 1;
		//                 break;
		//             }else{
		//                 result = -1;
		//                 break;
		//             }
		//         }
		//     }else if(k == suffix_i.length && k < suffix_j.length){
		//         result = -1;
		//         break;
		//     }else if(k < suffix_i.length && k == suffix_j.length){
		//         result = 1;
		//         break;
		//     }else{
		//         result = 0;
		//         break;
		//     }
		// }

		// return result; // この行は変更しなければいけない。 

		if (i==j) return 0;

		int m = 0;
		if (i<j) m = j;
		else m = i;

		for (int k = 0; k < mySpace.length - m; k++){
			if (mySpace[i+k] > mySpace[j+k]) return 1;
			if (mySpace[i+k] < mySpace[j+k]) return -1;
		}

		if (i < j) return 1;
		else return -1;
	}

	public void setSpace(byte []space) { 
		// suffixArrayの前処理は、setSpaceで定義せよ。
		mySpace = space; if(mySpace.length>0) spaceReady = true;
		// First, create unsorted suffix array.
		suffixArray = new int[space.length];
		// put all suffixes in suffixArray.
		for(int i = 0; i< space.length; i++) {
			suffixArray[i] = i; // Please note that each suffix is expressed by one integer.      
		}
		// ここに、int suffixArrayをソートするコードを書け。
		// もし、mySpace が"ABC"ならば、
		// suffixArray = { 0, 1, 2} となること求められる。
		// このとき、printSuffixArrayを実行すると
		//   suffixArray[ 0]= 0:ABC
		//   suffixArray[ 1]= 1:BC
		//   suffixArray[ 2]= 2:C
		// のようになるべきである。
		// もし、mySpace が"CBA"ならば
		// suffixArray = { 2, 1, 0} となることが求めらる。
		// このとき、printSuffixArrayを実行すると
		//   suffixArray[ 0]= 2:A
		//   suffixArray[ 1]= 1:BA
		//   suffixArray[ 2]= 0:CBA
		// のようになるべきである。

		// for (int i = 0; i < mySpace.length-1; i++){
		//     for (int j = 0; j < mySpace.length-i-1; j++){
		//         if (suffixCompare(suffixArray[j], suffixArray[j+1]) > 0){
		//             int temp = suffixArray[j+1];
		//             suffixArray[j+1] = suffixArray[j];
		//             suffixArray[j] = temp;
		//         }
		//     }
		// }

		// ソートの方法を取り換える
		// ヒープ木の構築
		ArrayList<Integer> heapTree = new ArrayList<Integer>();
		for (int i = 0; i < suffixArray.length; i++){
			heapTree.add(i);
			int idx = i+1;
			while(idx > 1){
				int idx_p = idx/2;

				if (suffixCompare(heapTree.get(idx_p-1), heapTree.get(idx-1)) < 0) break;

				int tmp = heapTree.get(idx_p-1);
				heapTree.set(idx_p-1, heapTree.get(idx-1));
				heapTree.set(idx-1, tmp);
				idx = idx_p;
			}
		}

		int n = 0;
		while(heapTree.size() != 0){
			suffixArray[n] = heapTree.get(0);
			n++;
			int idx=0;
			while (2*idx+2<heapTree.size()){
				if (suffixCompare(heapTree.get(2*idx+1),heapTree.get(2*idx+2)) > 0){
					heapTree.set(idx, heapTree.get(2*idx+2));
					idx = 2*idx+2;
				}
				else{
					heapTree.set(idx, heapTree.get(2*idx+1));
					idx = 2*idx+1;
				}
			}
			heapTree.remove(idx);
		}
	}

	// ここから始まり、指定する範囲までは変更してはならないコードである。

	public void setTarget(byte [] target) {
		myTarget = target; if(myTarget.length>0) targetReady = true;
	}

	public int frequency() {
		if(targetReady == false) return -1;
		if(spaceReady == false) return 0;
		return subByteFrequency(0, myTarget.length);
	}

	public int subByteFrequency(int start, int end) {
		// start, and end specify a string to search in myTarget,
		// if myTarget is "ABCD", 
		//     start=0, and end=1 means string "A".
		//     start=1, and end=3 means string "BC".
		// This method returns how many the string appears in my Space.
		// 
		/* This method should be work as follows, but much more efficient.
		   int spaceLength = mySpace.length;                      
		   int count = 0;                                        
		   for(int offset = 0; offset< spaceLength - (end - start); offset++) {
			boolean abort = false; 
			for(int i = 0; i< (end - start); i++) {
			 if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; }
			}
			if(abort == false) { count++; }
		   }
		*/
		// The following the counting method using suffix array.
		// 演習の内容は、適切なsubByteStartIndexとsubByteEndIndexを定義することである。
		int first = subByteStartIndex(start, end);
		int last1 = subByteEndIndex(start, end);
		return last1 - first;
	}
	// 変更してはいけないコードはここまで。

	private int targetCompare(int i, int j, int k) {
		// subByteStartIndexとsubByteEndIndexを定義するときに使う比較関数。
		// 次のように定義せよ。
		// suffix_i is a string starting with the position i in "byte [] mySpace".
		// When mySpace is "ABCD", suffix_0 is "ABCD", suffix_1 is "BCD", 
		// suffix_2 is "CD", and sufffix_3 is "D".
		// target_j_k is a string in myTarget start at j-th postion ending k-th position.
		// if myTarget is "ABCD", 
		//     j=0, and k=1 means that target_j_k is "A".
		//     j=1, and k=3 means that target_j_k is "BC".
		// This method compares suffix_i and target_j_k.
		// if the beginning of suffix_i matches target_j_k, it return 0.
		// if suffix_i > target_j_k it return 1; 
		// if suffix_i < target_j_k it return -1;
		// if first part of suffix_i is equal to target_j_k, it returns 0;
		//
		// Example of search 
		// suffix          target
		// "o"       >     "i"
		// "o"       <     "z"
		// "o"       =     "o"
		// "o"       <     "oo"
		// "Ho"      >     "Hi"
		// "Ho"      <     "Hz"
		// "Ho"      =     "Ho"
		// "Ho"      <     "Ho "   : "Ho " is not in the head of suffix "Ho"
		// "Ho"      =     "H"     : "H" is in the head of suffix "Ho"
		// The behavior is different from suffixCompare on this case.
		// For example,
		//    if suffix_i is "Ho Hi Ho", and target_j_k is "Ho", 
		//            targetCompare should return 0;
		//    if suffix_i is "Ho Hi Ho", and suffix_j is "Ho", 
		//            suffixCompare should return -1.
		//
		// ここに比較のコードを書け 
		//

		for (int n = 0; n < k-j; n++){
			if (i+n >= mySpace.length) return -1;

			if (mySpace[i+n] > myTarget[j+n]) return 1;
			if (mySpace[i+n] < myTarget[j+n]) return -1;
		}

		return 0; // この行は変更しなければならない。
	}


	private int subByteStartIndex(int start, int end) {
		//suffix arrayのなかで、目的の文字列の出現が始まる位置を求めるメソッド
		// 以下のように定義せよ。
		// The meaning of start and end is the same as subByteFrequency.
		/* Example of suffix created from "Hi Ho Hi Ho"
		   0: Hi Ho
		   1: Ho
		   2: Ho Hi Ho
		   3:Hi Ho
		   4:Hi Ho Hi Ho
		   5:Ho
		   6:Ho Hi Ho
		   7:i Ho
		   8:i Ho Hi Ho
		   9:o
		  10:o Hi Ho
		*/

		// It returns the index of the first suffix 
		// which is equal or greater than target_start_end.                         
	// Suppose target is set "Ho Ho Ho Ho"
		// if start = 0, and end = 2, target_start_end is "Ho".
		// if start = 0, and end = 3, target_start_end is "Ho ".
		// Assuming the suffix array is created from "Hi Ho Hi Ho",                 
		// if target_start_end is "Ho", it will return 5.                           
		// Assuming the suffix array is created from "Hi Ho Hi Ho",                 
		// if target_start_end is "Ho ", it will return 6.                
		//                                                                          
		// ここにコードを記述せよ。                                                 

		// for(int i = 0; i < suffixArray.length; i++){
		//     if (targetCompare(suffixArray[i], start, end) == 0) return i;
		// }
		// return suffixArray.length;

		// 探索を2分探索で実装する
		int left = 0;
		int right = suffixArray.length;
		while(left < right){
			int idx = (left+right)/2;
			if (targetCompare(suffixArray[idx], start, end) > -1) right = idx;
			if (targetCompare(suffixArray[idx], start, end) < 0) left = idx+1;
		}
		return right;
	}

	private int subByteEndIndex(int start, int end) {
		//suffix arrayのなかで、目的の文字列の出現しなくなる場所を求めるメソッド
		// 以下のように定義せよ。
		// The meaning of start and end is the same as subByteFrequency.
		/* Example of suffix created from "Hi Ho Hi Ho"
		   0: Hi Ho                                    
		   1: Ho                                       
		   2: Ho Hi Ho                                 
		   3:Hi Ho                                     
		   4:Hi Ho Hi Ho                              
		   5:Ho                                      
		   6:Ho Hi Ho                                
		   7:i Ho                                    
		   8:i Ho Hi Ho                              
		   9:o                                       
		  10:o Hi Ho                                 
		*/
		// It returns the index of the first suffix 
		// which is greater than target_start_end; (and not equal to target_start_end)
	// Suppose target is set "High_and_Low",
		// if start = 0, and end = 2, target_start_end is "Hi".
		// if start = 1, and end = 2, target_start_end is "i".
		// Assuming the suffix array is created from "Hi Ho Hi Ho",                   
		// if target_start_end is "Ho", it will return 7 for "Hi Ho Hi Ho".  
		// Assuming the suffix array is created from "Hi Ho Hi Ho",          
		// if target_start_end is"i", it will return 9 for "Hi Ho Hi Ho".    
		//                                                                   
		//　ここにコードを記述せよ                                           
		// for (int i = 0; i < suffixArray.length; i++){
		//     if ( targetCompare(suffixArray[suffixArray.length-i-1], start, end) == 0) return suffixArray.length-i;
		// }
		// return 0;

		// 探索を2分探索で実装する
		int left = 0;
		int right = suffixArray.length;
		while(left < right){
			int idx = (left+right)/2;
			if (targetCompare(suffixArray[idx], start, end) > 0) right = idx;
			if (targetCompare(suffixArray[idx], start, end) < 1) left = idx+1;
		}
		return left;
	}


	// Suffix Arrayを使ったプログラムのホワイトテストは、
	// privateなメソッドとフィールドをアクセスすることが必要なので、
	// クラスに属するstatic mainに書く方法もある。
	// static mainがあっても、呼びださなければよい。
	// 以下は、自由に変更して実験すること。
	// 注意：標準出力、エラー出力にメッセージを出すことは、
	// static mainからの実行のときだけに許される。
	// 外部からFrequencerを使うときにメッセージを出力してはならない。
	// 教員のテスト実行のときにメッセージがでると、仕様にない動作をするとみなし、
	// 減点の対象である。
	public static void main(String[] args) {
		Frequencer frequencerObject;
		try { // テストに使うのに推奨するmySpaceの文字は、"ABC", "CBA", "HHH", "Hi Ho Hi Ho".
			/*frequencerObject = new Frequencer();
			frequencerObject.setSpace("ABC".getBytes());
			frequencerObject.printSuffixArray();

			frequencerObject = new Frequencer();
			frequencerObject.setSpace("CBA".getBytes());
			frequencerObject.printSuffixArray();

			frequencerObject = new Frequencer();
			frequencerObject.setSpace("HHH".getBytes());
			frequencerObject.printSuffixArray();
			*/

			frequencerObject = new Frequencer();
			frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
			// frequencerObject.printSuffixArray();
			/* Example from "Hi Ho Hi Ho"    
			   0: Hi Ho                      
			   1: Ho                         
			   2: Ho Hi Ho                   
			   3:Hi Ho                       
			   4:Hi Ho Hi Ho                 
			   5:Ho                          
			   6:Ho Hi Ho
			   7:i Ho                        
			   8:i Ho Hi Ho                  
			   9:o                           
			  10:o Hi Ho                     
			*/

			// frequencerObject.setTarget("p".getBytes());
			// System.out.println("test1"+frequencerObject.targetCompare(3, 0, 1));
			//                                         
			// ****  Please write code to check subByteStartIndex, and subByteEndIndex
			frequencerObject.setTarget("p".getBytes());
			System.out.println(frequencerObject.frequency());
			/*System.out.println("Start");
			System.out.println("test3:"+frequencerObject.subByteStartIndex(0,2));
			System.out.println("test4:"+frequencerObject.subByteEndIndex(0,3));
			/*
			frequencerObject.setTarget("High_and_Low".getBytes());
			System.out.println("End");
			System.out.println("test5:"+frequencerObject.subByteEndIndex(0,2));
			System.out.println("test6:"+frequencerObject.subByteEndIndex(1,2));

			int result = frequencerObject.frequency();
			System.out.print("Freq = "+ result+" ");
			if(4 == result) { System.out.println("OK"); } else {System.out.println("WRONG"); }
			*/
		}
		catch(Exception e) {
			System.out.println("STOP");
		}
	}
}

