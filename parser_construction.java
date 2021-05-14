package com.company;

public class parser_construction extends parser_util
{
	// Defining starting rule and ending symbol
	static String Start_Sym = "prog";
	static int end_Sym_ind = Switch_Terms("$");

	static int num_Rules = Rules.length;

	// First Table for each of the Non-Terminal symbols to each of the Tokens
	// if a Token is in the First Set of a Non-Terminal symbol, then it is labeled with '1'
	static int[][] First_Set = new int[Non_Terms.length][Terminals.length];
	// Similar for the Follow Set
	static int[][] Follow_Set = new int[Non_Terms.length][Terminals.length];
	// see if the FIRST and FOLLOW sets of each rule have already been computed
	static int[] FIRST_Computed = new int[Non_Terms.length];
	static int[] FOLLOW_Computed = new int[Non_Terms.length];
	// Predictive Parsing Table; initialized to -1 for all entries
	static int[][] Parsing_Table = new int[Non_Terms.length][Terminals.length];

	static int[][] parser_construction(int if_print)
	{
		// Initialize the First and Follow Sets
		Init();

		// Construct FIRST Set
		// for each non-terminal symbol A, find its FIRST set among the rules
		for (int i = 0; i < Non_Terms.length; i++)
		{
			String A = Non_Terms[i];

			// find its row index in the FIRST table
			int row_ind = Switch_Non_Terms(A);

			// see if its FIRST has already been computed
			if (FIRST_Computed[row_ind] == 1)
				continue;
			// if not
			Find_First_Set(A);
		}
		if(if_print == 1) {
			Print_First_Set(First_Set);
		}
		// Construct FOLLOW Set
		// include $ to the FOLLOW of the starting symbol
		Follow_Set[Switch_Non_Terms(Start_Sym)][end_Sym_ind] = 1;

		// for each non-terminal symbol A, find its FOLLOW set among the rules
		for (int i = 0; i < Non_Terms.length; i++)
		{
			String A = Non_Terms[i];

			// find its row index in the FOLLOW table
			int row_ind = Switch_Non_Terms(A);

			// see if its FOLLOW has already been computed
			if (FOLLOW_Computed[row_ind] == 1)
				continue;
			// if not
			Find_Follow_Set(A);
		}
		if(if_print == 1) {
			Print_Follow_Set(Follow_Set);
		}

		Construct_Parser();

		if(if_print == 1) {
			Print_Parsing_Table(Parsing_Table);
		}
		return Parsing_Table;
	}


	static void Find_First_Set(String A)
	{
		// find its row index in the FIRST table
		int row_ind = Switch_Non_Terms(A);

		for (int i = 0; i < num_Rules; i++)
		{
			// if current rule's LHS is NOT A
			if (!Rules[i][0].equals(A))
				continue;

			/* To be implemented */
			// Can be multiple Rules[i][0] that equals A. Those represent OR!
			String[] stri = Rules[i][1].split(" ");
			// If initial rule goes to #
			if (Rules[i][1].equals("#")) {
				First_Set[row_ind][0] = 1;
				continue;
			// If terminal value found
			} else if (Switch_Terms(stri[0]) != -1) {
				First_Set[row_ind][Switch_Terms(stri[0])] = 1;
				continue;
			} else {
				// If no terminal and no #
				for(int k=0; k < stri.length; k++) {
					int ntk = Switch_Non_Terms(stri[k]);
					// If we meet a non-terminal
					if (ntk != -1) {
						// If not previously calculated, call recursively
						if(FIRST_Computed[ntk] != 1){
							Find_First_Set(stri[k]);
						}
						int brk = 0;
						// Go through and add subset to First_Set for A
						for (int l = 0; l < Terminals.length; l++) {
							if (First_Set[ntk][l] == 1) {
								First_Set[row_ind][l] = 1;
								// If hit #, break
								if(l == 0){
									brk = 1;
								}
							}
						}
						// If we don't hit a #, break
						if (brk == 0) {
							break;
						}
					// If we meet a terminal
					} else {
						First_Set[row_ind][Switch_Terms(stri[k])] = 1;
						break;
					}
				}
			}
		}

		FIRST_Computed[row_ind] = 1;
	}


	static void Find_Follow_Set(String B)
	{
		// find its row index in the FOLLOW table
		int row_ind = Switch_Non_Terms(B);

		for (int i = 0; i < num_Rules; i++)
		{
			// see if B exist in the RHS
			int contains_B = 0;
			int j;
			String[] post_Rules = Rules[i][1].split(" ");
			for (j = 0; j < post_Rules.length; j++)
			{
				if (post_Rules[j].equals(B))
				{
					contains_B = 1;
					break;
				}
			}

			if (contains_B == 0)
				continue;

			/* To be implemented */
			int checkLast = 1;
			for(int k = j+1; k < post_Rules.length; k++){
				// Check to see if non terminal symbol
				int sntk = Switch_Non_Terms(post_Rules[k]);
				if (sntk != -1){
					// Copy starting set to follow set
					int checkNext = 0;
					for(int l=0; l<Terminals.length; l++){
						if(First_Set[sntk][l] == 1) {
							// If hit #, check next k
							if(l == 0){
								checkNext = 1;
							}else{
								Follow_Set[row_ind][l] = 1;
							}
						}
					}

					// If no #, don't check the next k
					if(checkNext == 0){
						checkLast = 0;
						break;
					}
				// If terminal symbol, break (Can't be # since # only exists by itself)
				} else {
					sntk = Switch_Terms(post_Rules[k]);
					Follow_Set[row_ind][sntk] = 1;
					checkLast = 0;
					break;
				}
			}

			// make sure we avoid infinite loop
			if (checkLast == 1 && !Rules[i][0].equals(B)){
				int snti = Switch_Non_Terms(Rules[i][0]);
				// Populate Follow_Set if not already
				if(FOLLOW_Computed[snti] != 1) {
					Find_Follow_Set(Rules[i][0]);
				}
				// Set as subset
				for(int k=0; k<Terminals.length; k++){
					if(Follow_Set[snti][k] == 1) {
						Follow_Set[row_ind][k] = 1;
					}
				}
			}
		}

		FOLLOW_Computed[row_ind] = 1;
	}



	static int Construct_Parser()
	{
		/* To be implemented */
		for(int i=0; i<Non_Terms.length; i++){
			// Look up each term for each non_term
			for(int j=0; j<Terminals.length; j++){
				// If term discovered
				if(First_Set[i][j] == 1){
					// if term is not empty
					if(j != 0) {
						int ruleNum = -1;

						// Look up rule and locate number
						for (int k = 0; k < num_Rules; k++) {
							// If Rule left side matches with current non_term
							if (Switch_Non_Terms(Rules[k][0]) == i) {
								// Find first
								String first = Rules[k][1].split(" ")[0];
								int id = Switch_Non_Terms(first);

								// if first is non-term
								if (id != -1) {
									// Test to see if the terminal matches
									if (First_Set[id][j] == 1) {
										// Record rule # and break
										ruleNum = k;
										break;
									}
									// if first is term
								} else {
									id = Switch_Terms(first);
									// Check if terminal matches
									if (j == id) {
										// Record rule # and break
										ruleNum = k;
										break;
									}
								}
							}
						}

						// Set parsing table entry
						Parsing_Table[i][j] = ruleNum;
					} else {
						int position = -1;
						// Run through rules
						for (int k = 0; k < num_Rules; k++){
							// Found potential A --> alpha position
							if (Switch_Non_Terms(Rules[k][0]) == i) {
								// Analyze first term of the corresponding rule
								String first = Rules[k][1].split(" ")[0];
								int s = Switch_Terms(first);

								// If the term is terminal and equal to 0, found # position
								if(s == 0){
									position = k;
									break;
								// If term is non-terminal, analyze established First_Set of non-term
								} else if (s == -1){
									s = Switch_Non_Terms(first);
									// found # position
									if(First_Set[s][0] == 1){
										position = k;
										break;
									}
								}
							}
						}

						// For the corresponding follow sets, set position accordingly
						for(int k = 0; k < Terminals.length; k++){
							if(Follow_Set[i][k] == 1){
								Parsing_Table[i][k] = position;
							}
						}
					}
				}
			}
		}
		return 0;
	}


	static void Init()
	{
		// Initialization
		for (int i = 0; i < Non_Terms.length; i++)
		{
			FIRST_Computed[i] = 0;
			FOLLOW_Computed[i] = 0;

			for (int j = 0; j < Terminals.length; j++)
			{
				First_Set[i][j] = 0;
				Follow_Set[i][j] = 0;
				Parsing_Table[i][j] = -1;
			}
		}
	}


}
