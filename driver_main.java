package com.company;
import java.util.ArrayList;
import java.util.List;


public class driver_main extends parser_construction
{
	static List<String> Parsing_Stack = new ArrayList<String>();
	static List<String> Input;
	static List<String> Parsing_Actions = new ArrayList<String>();

	public static void main(String[] args)
	{
		// Construct Parsing Table
		int if_print = 0;
		int[][] Parsing_Table = parser_construction(if_print);

		if (Parsing_Table == null)
			return ;

		// Initialize Parsing Stack
		Parsing_Stack.add("$");
		Parsing_Stack.add("prog");

		// Read-Input
		Input = token_scanner.Scanner_Main(if_print);

		// while not reaching $
		while (!Input.get(0).equals("$"))
		{
			// Reads first input token
			String curr_token = Input.get(0);
			int token_ind = Switch_Terms(curr_token);

			// Reads top symbol from stack.
			String curr_symbol = Parsing_Stack.get(Parsing_Stack.size()-1);

			// check if symbol is terminal or non-terminal
			int if_is_non_term = Switch_Non_Terms(curr_symbol);
			int if_is_terminal = Switch_Terms(curr_symbol);
			// if non-terminal, then see if there is a rule
			if (if_is_non_term != -1)
			{
				/* To be Implemented */
				// Rule exists for curr non_term symbol involving cur token
				int checkPT = Parsing_Table[if_is_non_term][token_ind];
				// Rule Exists in parsing table
				if (checkPT != -1){
					// Keep track in Parsing_Actions
					Parsing_Actions.add(Rules[checkPT][0] + " --> " + Rules[checkPT][1]);
					// to be push onto stack backwards
					String[] push = Rules[checkPT][1].split(" ");
					// pop current symbol (top of stack)
					Parsing_Stack.remove(Parsing_Stack.size()-1);
					// push new values onto stack.
					for(int i=push.length-1; i>-1; i--){
						// Do not push empty string onto stack
						if(!push[i].equals("#")) {
							Parsing_Stack.add(push[i]);
						}
					}
				} else {
					System.out.println("Syntax Error!");
					return ;
				}
			}

			// if terminal, then see if match
			else if (if_is_terminal != -1)
			{
				/* To be Implemented */
				if(if_is_terminal == token_ind){
					Parsing_Actions.add("match");
					Input.remove(0);
					Parsing_Stack.remove(Parsing_Stack.size()-1);
				}else{
					System.out.println("Syntax Error!");
					return ;
				}
			}

			// else there must be syntax error
			else
			{
				System.out.println("Syntax Error!");
				return ;
			}
		}

		// if front of Input is $, and top of Stack is $
		if (Input.get(0).equals("$") && Parsing_Stack.get(Parsing_Stack.size()-1).equals("$"))
		{
			System.out.println("Parsing Successful!");
			Parsing_Actions.add("accept");
			return ;
		}
		else
		{
			System.out.println("Syntax Error!");
			return ;
		}

	}

	static void print_parsing_update()
	{
		System.out.print("Stack:\n\t");
		for (int i = 0; i < Parsing_Stack.size(); i++)
			System.out.print(Parsing_Stack.get(i)+" ");

		System.out.print("\nInput:\n\t");
		for (int i = 0; i < Input.size(); i++)
			System.out.print(Input.get(i)+" ");

		System.out.print("\nAction:\n\t");
		System.out.println(Parsing_Actions.get(Parsing_Actions.size()-1)+"\n");
	}

}
