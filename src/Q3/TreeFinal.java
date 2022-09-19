package Q3;

import java.util.*;

public class TreeFinal
{
	public static void main(String[] args)
	{
		Map<String, List<String>> tree = makeTree();
		Map<String, String> parents = makeParents(tree);

		Set<String> nodes = new HashSet<>(tree.keySet());
		nodes.addAll(parents.keySet());

		for (String node : nodes)
			System.out.println(String.join("/", findPath(parents, node)));

		// 원하는 노드 경로
		// System.out.println(String.join("/", findPath(parents, "L")));

		DFS(tree,"A");
	}
	private static Map<String, String> makeParents(Map<String, List<String>> tree)
	{
		Map<String, String> parents = new HashMap<>();
		for (Map.Entry<String, List<String>> e : tree.entrySet())
			for (String child : e.getValue())
				parents.put(child, e.getKey());
		return parents;
	}

	private static Map<String, List<String>> makeTree()
	{
		Map<String, List<String>> tree = new HashMap<>();

		tree.put("A", Arrays.asList("B", "C", "D"));
		tree.put("B", Arrays.asList("E", "F"));
		tree.put("C", Collections.singletonList("G"));
		tree.put("D", Arrays.asList("H", "I"));
		tree.put("E", Arrays.asList("J"));
		tree.put("G", Arrays.asList("K"));
		tree.put("K", Arrays.asList("L"));
		return tree;
	}

	public static List<String> findPath(Map<String, String> parents, String node)
	{
		List<String> path = new ArrayList<>();
		path.add(node);
		while ((node = parents.get(node)) != null)
			path.add(0, node);
		return path;
	}

	public static void DFS(Map<String, List<String>> node, String start)
	{
		System.out.print(start + " ");
		List<String> children = node.get(start);
		if (children==null)
			return;
		for (String child : children)
			DFS(node, child);
	}
}
