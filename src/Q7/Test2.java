package Q7;

import java.util.Scanner;

public class Test2
{
	public static void main(String[] args)
	{
		try (Scanner scanner = new Scanner(System.in))
		{
			while (true)
			{
				System.out.println("값 입력");
				String value = scanner.nextLine();

				if (value.equals("exit")) break;

				if ("+-*/=".contains(String.valueOf(value.charAt(0))) || "+-*/=".contains(String.valueOf(value.charAt(value.length() - 1))))
					System.out.println("잘못된 입력");
				int operLocation = 0;
				int beforeLocation = 0;
				for (int i = 0; i < value.length(); i++)
				{
					if ("+-*/=".contains(String.valueOf(value.charAt(i))))
					{
						operLocation = i;
						if (beforeLocation + 1 == operLocation && operLocation != 1)
						{
							System.out.println("연산자 중복입력");
							continue;
						}
						else
						{
							int a = 0;
							String operator = null;
							int b = 0;

							// 연산자 갯수 구하기
							int opers = 0;
							for (int q = 0; q < value.length(); q++)
								if ("+-*/=".contains(String.valueOf(value.charAt(q))))
									opers++;
							if (opers == 0)
							{
								System.out.println("잘못된 입력");
								continue;
							}
							if (opers == 1)
							{
								for (int j = 0; j < value.length(); j++)
								{
									if ("+-*/=".contains(String.valueOf(value.charAt(j))))
										a = Integer.parseInt(value.substring(0, j));
									operator = value.substring(operLocation, operLocation + 1);
								}
								b = Integer.parseInt(value.substring(operLocation + 1, value.length()));
								System.out.println(calculate(a, operator, b));
							}
							else if (opers > 3)
								for (int l = 0; l < opers; l++)
								{
									for (int j = 0; j < value.length(); j++)
									{
										if ("+-*/=".contains(String.valueOf(value.charAt(j))))
											a = Integer.parseInt(value.substring(0, j));
										operator = value.substring(operLocation, operLocation + 1);
									}
									for (int k = 0; k<operLocation+1; k++)
									{
										if ("+-*/=".contains(String.valueOf(value.charAt(k))))
											b = Integer.parseInt(value.substring(operLocation+1, k));
									}
									a = calculate(a, operator, b);
								}
						}
						beforeLocation = operLocation;
					}
				}
			}
		}
	}

	private static int calculate(int a, String operator, int b)
	{
		int answer = 0;
		if (operator.equals("+")) answer = a + b;
		if (operator.equals("-")) answer = a - b;
		if (operator.equals("*")) answer = a * b;
		if (operator.equals("/")) answer = a / b;
		return answer;
	}
}
