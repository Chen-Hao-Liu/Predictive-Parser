package com.company;

public class parser_util
{
	static String[] Terminals = {"#", "PROGRAM", "ID", "(", ")", ";", ".", ",", "VAR", ":", "INT", "REAL",
			 					 "BEGIN", "END", "=", "MULOP", "NUM", "$", "ADDOP", "RELOP",
			 					 "{", "}", "IF", "THEN", "ELSE", "WHILE", "DO", "ARRAY", "OF", "[", "]", "NOT", "AND"};

	static String[] Non_Terms = {"prog", "id_list", "id_list_p", "decls", "decls_p", "type", "strd_type",
								 "comp_stmt", "opt_stmts", "stmt_list", "stmt_list_p", "stmt",
								 "variable", "variable_p", "exp_list", "exp_list_p", "exp", "exp_p", "num_list", "num_list_p",
								 "simple_exp", "simple_exp_p", "term", "term_p", "factor", "factor_p", "sign"};

	public static String[][] Rules = {
			{"prog", 				"PROGRAM ID ; decls comp_stmt ."},		// P0
			{"id_list", 		"ID id_list_p"},											// P1
			{"id_list_p", 	", ID id_list_p"},										// P2
			{"id_list_p", 	"#"},																	// P3
			{"decls", 			"decls_p"},														// P4
			{"decls_p", 		"VAR id_list : type ; decls_p"},			// P5
			{"decls_p", 		"#"},																	// P6
			{"type",				"strd_type"},													// P7
			{"type", 				"ARRAY [ NUM ; NUM ] OF strd_type"},	// P8
			{"strd_type", 	"INT"},																// P9
			{"strd_type",		"REAL"},															// P10
			{"comp_stmt",		"BEGIN opt_stmts END"},								// P11
			{"opt_stmts",		"stmt_list"},													// P12
			{"opt_stmts",		"#"},																	// P13
			{"stmt_list", 	"stmt stmt_list_p"},									// P14
			{"stmt_list_p", "; stmt stmt_list_p"},								// P15
			{"stmt_list_p", "#"},																	// P16
			{"stmt",				"variable = exp"},										// P17
			{"stmt",				"IF exp_list THEN { stmt_list } ELSE { stmt_list }"},
																														// P18
			{"stmt",				"WHILE exp_list DO { stmt_list }"},		// P19
			{"variable",		"ID variable_p"},											// P20
			{"variable_p",	"[ exp ]"},														// P21
			{"variable_p",	"#"},																	// P22
			{"exp_list",		"exp exp_list_p"},										// P23
			{"exp_list_p",	", exp exp_list_p"},									// P24
			{"exp_list_p",	"AND exp exp_list_p"},								// P25
			{"exp_list_p",	"#"},																	// P26
			{"exp",					"{ num_list }"},											// P27
			{"exp", 				"simple_exp exp_p"},									// P28
			{"exp_p",				"RELOP simple_exp"},									// P29
			{"exp_p",				"#"},																	// P30
			{"num_list",		"NUM num_list_p"},										// P31
			{"num_list_p",	", NUM num_list_p"},									// P32
			{"num_list_p",	"#"},																	// P33
			{"simple_exp",	"term simple_exp_p"},									// P34
			{"simple_exp", 	"sign term simple_exp_p"},						// P35
			{"simple_exp_p", "ADDOP term simple_exp_p"},					// P36
			{"simple_exp_p", "#"},																// P37
			{"term", 				"factor term_p"},											// P38
			{"term_p", 			"MULOP factor term_p"},								// P39
			{"term_p", 			"#"},																	// P40
			{"factor", 			"ID factor_p"},												// P41
			{"factor",			"NUM"},																// P42
			{"factor",			"( exp_list )"},											// P43
			{"factor",			"NOT factor"},												// P44
			{"factor_p",		"( exp_list )"},											// P45
			{"factor_p",		"[ exp_list ]"},											// P46
			{"factor_p",		"#"},																	// P47
			{"sign",				"ADDOP"}};														// P48

	public static int Switch_Non_Terms(String A)
	{
		switch (A)
		{
			case "prog":
				return 0;
			case "id_list":
				return 1;
			case "id_list_p":
				return 2;
			case "decls":
				return 3;
			case "decls_p":
				return 4;
			case "type":
				return 5;
			case "strd_type":
				return 6;
			case "comp_stmt":
				return 7;
			case "opt_stmts":
				return 8;
			case "stmt_list":
				return 9;
			case "stmt_list_p":
				return 10;
			case "stmt":
				return 11;
			case "variable":
				return 12;
			case "variable_p":
				return 13;
			case "exp_list":
				return 14;
			case "exp_list_p":
				return 15;
			case "exp":
				return 16;
			case "exp_p":
				return 17;
			case "num_list":
				return 18;
			case "num_list_p":
				return 19;
			case "simple_exp":
				return 20;
			case "simple_exp_p":
				return 21;
			case "term":
				return 22;
			case "term_p":
				return 23;
			case "factor":
				return 24;
			case "factor_p":
				return 25;
			case "sign":
				return 26;
			default:
				return -1;
		}
	}


	public static int Switch_Terms(String A)
	{
		switch (A)
		{
			case "#":
				return 0;
			case "PROGRAM":
				return 1;
			case "ID":
				return 2;
			case "(":
				return 3;
			case ")":
				return 4;
			case ";":
				return 5;
			case ".":
				return 6;
			case ",":
				return 7;
			case "VAR":
				return 8;
			case ":":
				return 9;
			case "INT":
				return 10;
			case "REAL":
				return 11;
			case "BEGIN":
				return 12;
			case "END":
				return 13;
			case "=":
				return 14;
			case "MULOP":
				return 15;
			case "NUM":
				return 16;
			case "$":
				return 17;
			case "ADDOP":
				return 18;
			case "RELOP":
				return 19;
			case "{":
				return 20;
			case "}":
				return 21;
			case "IF":
				return 22;
			case "THEN":
				return 23;
			case "ELSE":
				return 24;
			case "WHILE":
				return 25;
			case "DO":
				return 26;
			case "ARRAY":
				return 27;
			case "OF":
				return 28;
			case "[":
				return 29;
			case "]":
				return 30;
			case "NOT":
				return 31;
			case "AND":
				return 32;
			default:
				return -1;
		}
	}

	static void Print_First_Set(int[][] First_Set)
	{
		System.out.println("FIRST Set:");
		for (int i = 0; i < Non_Terms.length; i++)
		{
			if (Non_Terms[i].length() >= 8)
				System.out.print(Non_Terms[i]+"\t");
			else
				System.out.print(Non_Terms[i]+"\t\t");

			for (int j = 0; j < Terminals.length; j++)
			{
				if (First_Set[i][j] == 1)
					System.out.print(Terminals[j]+"\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	static void Print_Follow_Set(int[][] Follow_Set)
	{
		System.out.println("FOLLOW Set:");
		for (int i = 0; i < Non_Terms.length; i++)
		{
			if (Non_Terms[i].length() >= 8)
				System.out.print(Non_Terms[i]+"\t");
			else
				System.out.print(Non_Terms[i]+"\t\t");

			for (int j = 0; j < Terminals.length; j++)
			{
				if (Follow_Set[i][j] == 1)
					System.out.print(Terminals[j]+"\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	static void Print_Parsing_Table(int[][] Parsing_Table)
	{
		System.out.println("Predictive Parser:");
		for (int i = 0; i < Non_Terms.length; i++)
		{
			if (Non_Terms[i].length() >= 8)
				System.out.print(Non_Terms[i]+"\t");
			else
				System.out.print(Non_Terms[i]+"\t\t");

			for (int j = 0; j < Terminals.length; j++)
			{
				if (Parsing_Table[i][j] >= 0)
					System.out.print(Terminals[j]+": P"+(Parsing_Table[i][j]+1)+"\t\t");
			}
			System.out.println();
		}
		System.out.println();
	}
}
