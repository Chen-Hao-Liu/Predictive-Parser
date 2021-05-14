package com.company;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class token_scanner
{
	// for checking NUMBER regex
	static String LeadingNums = "123456789";
	static String AllNums = "0" + LeadingNums;
	// for checking STRING regex
	static String Letters_lower = "abcdefghijklmnopqrstuvwxyz";
	static String Letters_capital = Letters_lower.toUpperCase();
	static String AllLetters = Letters_lower + Letters_capital;
	static String All_Letters_Nums = AllNums + AllLetters;

	// List of scanned TOKENs; and if not reserved words, their values
	static List<String> token_sequence = new ArrayList<String>();
	static List<String> StringNum_sequence = new ArrayList<String>();

	static String[] token_array;
	static String[] StrNum_array;

	public static List<String> Scanner_Main(int if_print)
	{
		// read the input file
		String Input = "";

		try
		{
			//File input_file = new File("Example_Input.txt");
			// I had to change the input path to find the file.
			File input_file = new File("./src/com/company/Example_Input.txt");
			Scanner sc = new Scanner(input_file);

			while (sc.hasNextLine())
				Input += sc.nextLine()+"\n";

			sc.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Cannot find specified input file.");
		}


		// scan the input
		int succeeded = scanning(Input);

		token_array = new String[token_sequence.size()];
		StrNum_array = new String[token_sequence.size()];

		if (succeeded == 1)
		{
			int k = 0;

			for (int j = 0; j < token_sequence.size(); j++)
			{
				String curr_token = token_sequence.get(j);
				token_array[j] = curr_token;

				if (if_print == 1)
					System.out.print(curr_token);

				if ((curr_token == "NUM") || (curr_token == "ID"))
				{
					String curr_StrNum = StringNum_sequence.get(k);
					k++;
					StrNum_array[j] = curr_StrNum;

					if (if_print == 1)
						System.out.print(":\t" + curr_StrNum);

				}
				if (if_print == 1)
					System.out.println("");
			}

			token_sequence.add("$");
			return token_sequence;
		}

		return null;
	}


	// Input program as a String, then return all the tokens
	static int scanning(String Input)
	{
		for (int i = 0; i < Input.length(); i++)
		{
			char c = Input.charAt(i);
			String token = null;

			// if current token is complete, set to 1
			// else remain 0
			int full_token_flag = 0;

			switch (c)
			{
				case ' ':
				case '\n':
				case '\t':
					full_token_flag = 1;
					break;

				case ';':
					token = ";";
					full_token_flag = 1;
					break;

				case ',':
					token = ",";
					full_token_flag = 1;
					break;

				case ':':
					token = ":";
					full_token_flag = 1;
					break;

				case '.':
					token = ".";
					full_token_flag = 1;
					break;

				case '*':
					token = "MULOP";
					full_token_flag = 1;
					break;

				case '/':
					token = "MULOP";
					full_token_flag = 1;
					break;

				case '+':
					token = "ADDOP";
					full_token_flag = 1;
					break;

				case '-':
					token = "ADDOP";
					full_token_flag = 1;
					break;

				case '!':
					token = "NOT";
					full_token_flag = 1;
					break;

				case '&':
					token = "AND";
					full_token_flag = 1;
					break;

				case '(':
					token = "(";
					full_token_flag = 1;
					break;

				case ')':
					token = ")";
					full_token_flag = 1;
					break;

				case '{':
					token = "{";
					full_token_flag = 1;
					break;

				case '}':
					token = "}";
					full_token_flag = 1;
					break;

				case '[':
					token = "[";
					full_token_flag = 1;
					break;

				case ']':
					token = "]";
					full_token_flag = 1;
					break;

				default:
					// if c is either a number or a letter
					if (All_Letters_Nums.indexOf(c) != -1)
						break;
					else
					{
						if ((c == '=') || (c == '<') || (c == '>'))
						{
							if (Input.charAt(++i) == '=')
							{
								token = "RELOP";
								full_token_flag = 1;
								break;
							}
							else
							{
								i--;
								switch (c)
								{
									case '=':
										token = "=";
										full_token_flag = 1;
										break;
									default:
										token = "RELOP";
										full_token_flag = 1;
										break;
								}
							}
						}
						else
						{
							// otherwise it has not being defined
							System.out.println("Undefined SYMBOL: " + c);
							return 0;
						}
					}
			}

			// if current token is valid and is a full token
			if ((token != null) && (full_token_flag == 1))
				token_sequence.add(token);
			else
			{
				// if current token is not complete
				// then check for regular expression
				if (full_token_flag == 0)
				{
					String reg_exp = c + "";

					// if potentially a number, where the Most-Significant Digit
					// ranges from 0 to 9
					if (AllNums.indexOf(c) != -1)
					{
						int already_has_dot = 0;
						while ( (AllNums.indexOf( Input.charAt(++i) ) != -1) || ((already_has_dot == 0) && (Input.charAt(i) == '.')) )
						{
							reg_exp += Input.charAt(i);

							if (Input.charAt(i) == '.')
								already_has_dot = 1;
						}
						i--;

						token = "NUM";
						full_token_flag = 1;
						token_sequence.add(token);
						StringNum_sequence.add(reg_exp);

					}

					// else if potentially a string, where the Most-Significant Bit
					// is from A to Z, or from a to z
					else if (AllLetters.indexOf(c) != -1)
					{
						while (All_Letters_Nums.indexOf( Input.charAt(++i) ) != -1)
						{
							reg_exp += Input.charAt(i);
						}
						i--;

						// See if the expression is for reserved words
						switch (reg_exp)
						{
							case "prog":
								token = "PROGRAM";
								full_token_flag = 1;
								break;

							case "begin":
								token = "BEGIN";
								full_token_flag = 1;
								break;

							case "end":
								token = "END";
								full_token_flag = 1;
								break;

							case "if":
								token = "IF";
								full_token_flag = 1;
								break;

							case "then":
								token = "THEN";
								full_token_flag = 1;
								break;

							case "else":
								token = "ELSE";
								full_token_flag = 1;
								break;

							case "while":
								token = "WHILE";
								full_token_flag = 1;
								break;

							case "do":
								token = "DO";
								full_token_flag = 1;
								break;

							case "var":
								token = "VAR";
								full_token_flag = 1;
								break;

							case "int":
								token = "INT";
								full_token_flag = 1;
								break;

							case "real":
								token = "REAL";
								full_token_flag = 1;
								break;

							case "array":
								token = "ARRAY";
								full_token_flag = 1;
								break;

							case "of":
								token = "OF";
								full_token_flag = 1;
								break;

							case "function":
								token = "FUNCTION";
								full_token_flag = 1;
								break;

							case "procedure":
								token = "PROEDURE";
								full_token_flag = 1;
								break;

							default:
								token = "ID";
								full_token_flag = 1;
								break;
						}

						token_sequence.add(token);
						if (token == "ID")
							StringNum_sequence.add(reg_exp);
					}
				}
			}
		}
		return 1;
	}
}
