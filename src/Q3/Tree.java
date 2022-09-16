package Q3;

import java.util.*;

public class Tree
{
	static class Node
	{
		String val;
		Node lt, ct, rt;
		public Node(String val)
		{
			this.val = val;
			lt = ct = rt = null;
		}
	}
	static void DFS(Node node)
	{
		if (node==null)
			return;
		// node 출력하고 왼쪽부터 호출
		System.out.print(node.val + " ");
		DFS(node.lt);
		DFS(node.ct);
		DFS(node.rt);

//		Stack<Node> stack = new Stack<>();
//		stack.push(node);
//
//		while (!stack.isEmpty())
//		{
//			Node node = stack.pop();
//
//			System.out.print(node.val+" ");
//			if (node.rt!=null)
//				stack.push(node.rt);
//			if (node.ct!=null)
//				stack.push(node.ct);
//			if (node.lt!=null)
//				stack.push(node.lt);
//		}
	}
	static void BFS(Node node)
	{
		if (node==null)
			return;
		// add, offer 모두 해당 큐 맨 뒤에 값 삽입
		// add 는 큐가 꽉 찬 경우 예외 발생, offer 는 false 리턴
		// LinkedList 는 다음 노드가 있는 곳으로 가려고 다른 간접적인 경로를 거쳐감
		// ArrayDeque 는 다음 노드에 대한 추가 참조를 유지할 필요가 없으므로 LinkedList 보다 메모리 효율적
		Queue<Node> q = new ArrayDeque<>();
		q.offer(node);
		while (!q.isEmpty())
		{
			Node n = q.poll();
			System.out.print(n.val+" ");
			if (n.lt!=null)
				q.offer(n.lt);
			if (n.ct!=null)
				q.offer(n.ct);
			if (n.rt!=null)
				q.offer(n.rt);
		}
	}
	public static void findPath(Node node)
	{
		// Deque(Double-Ended Queue) : 큐의 양쪽에서 데이터를 삽입과 삭제를 할 수 있는 자료구조
		Deque<String> path = new ArrayDeque<>();
		findPath(node,path);
	}

	public static void findPath(Node node, Deque<String> path)
	{
		if (node==null)
			return;
		// 현재 노드를 경로에 포함
		path.add(node.val);

		System.out.print(path.getLast()+"의 경로 : ");
		for (String p:path)
		{
			System.out.print("/"+p);
		}
		System.out.println();

		findPath(node.lt, path);
		findPath(node.ct, path);
		findPath(node.rt, path);

		// 현재 경로 지움
		path.removeLast();
	}

	public static void main(String[] args) {

		Node root = new Node("A");
		root.lt = new Node("B");
		root.ct = new Node("C");
		root.rt = new Node("D");
		root.lt.lt = new Node("E");
		root.lt.ct = new Node("F");
		root.ct.lt = new Node("G");
		root.rt.lt = new Node("H");
		root.rt.ct = new Node("I");
		root.lt.lt.lt = new Node("J");
		root.ct.lt.lt = new Node("K");
		root.ct.lt.lt.lt = new Node("L");

		DFS(root);
		System.out.println();
		BFS(root);
		System.out.println();
		findPath(root);
	}
}