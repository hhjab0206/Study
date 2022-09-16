package Q3;

import java.util.*;

public class TreeMap3
{
	public static void DFS(Map<Character, String> node, char start)
	{
		if (node == null)
			return;
		System.out.print(start + " ");
		String children = node.get(start);
		for (char child : children.toCharArray())
			DFS(node, child);
	}
	public static void BFS(Map<Character, String> node, char start)
	{
		if (node == null)
			return;
		Queue<Character> children = new ArrayDeque<>();
		children.add(start);
		while (!children.isEmpty())
		{
			char now = children.remove();
			System.out.print(now + " ");
			String linkedNodes = node.get(now);
			for (char childNode : linkedNodes.toCharArray())
				children.add(childNode);
		}
	}
	public static void findPath(Map<Character, String> node, char find)
	{
		for (Map.Entry<Character, String> e : node.entrySet())
			if (e.getValue().contains(String.valueOf(find)))
				findPath(node, e.getKey());
		System.out.print("/"+find);
	}
	public static void main(String[] args)
	{
		Map<Character, String> tree = new HashMap<>();
		tree.put('A', "BCD");
		tree.put('B', "EF");
		tree.put('C', "G");
		tree.put('D', "HI");
		tree.put('E', "J");
		tree.put('F', "");
		tree.put('G', "K");
		tree.put('H', "");
		tree.put('I', "");
		tree.put('J', "");
		tree.put('K', "L");
		tree.put('L', "");

		DFS(tree, 'A');
		System.out.println();
		BFS(tree, 'A');
		System.out.println();
		findPath(tree, 'L');
	}
}