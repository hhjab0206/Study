package Q1;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class Abc
{
	public static void main(String[] args)
	{
		// 1-a
		String str = "aaa:bbb:ccc";
		System.out.println("aaa:bbb:ccc".substring(4, 7));

        // split 사용
		System.out.println("aaa:bbb:ccc".split(":")[1]);
		// indexOf 사용
		int idx = str.indexOf(":");
		System.out.println(str.substring(idx +1,str.indexOf(':',idx+1)));

		// 1-b
		String[] arr = new String[]{"aaa", "bbb", "_aaa", "bb_"};
		for (String obj : arr)
			if (obj.startsWith("_"))
				System.out.println(obj);
		// 1줄코드 : stream 사용 (스트림, 람다 = 양날의 검)
		// 람다 : Anonymous 클래스
		// Arrays.stream(arr).filter(x -> x.startsWith("_")).forEach(v -> System.out.println(v));
		Arrays.stream(arr).filter(x -> x.startsWith("_")).forEach(System.out::println);

		// 1-c
		for (String obj : arr)
			if (obj.endsWith("_"))
				System.out.println(obj);
		// 1줄 코드
		Arrays.stream(arr).filter(x -> x.endsWith("_")).forEach(System.out::println);

	}

}
