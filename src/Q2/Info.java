package Q2;

import java.util.*;

public class Info
{
	public static void main(String[] args)
	{
//		System.out.println(System.getenv());

//		Map<String, String> osInfo = System.getenv();
//		osInfo.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);

		// OS 환경 정보
//		System.getenv().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);

		// Java 환경 정보
//		Map javaInfo = System.getProperties();
//		javaInfo.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);

		System.out.println("정렬");
		System.out.println(new TreeMap<>(System.getProperties()));

		System.out.println("Java 환경 정보");
		for (Map.Entry<Object, Object> e:new TreeMap<>(System.getProperties()).entrySet())
			System.out.println(e.getKey()+" = "+e.getValue());

		System.out.println("OS 환경 정보");
		for (Map.Entry<String, String> e:new TreeMap<>(System.getenv()).entrySet())
			System.out.println(e.getKey()+" = "+e.getValue());
	}
}
