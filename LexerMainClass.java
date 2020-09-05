import java.io.*;
import java.util.*;
// 1111111111111111111111111111111111111111111111111111111111111111111111111111111
enum TokenType{IDE,EQUAL,ADD,SUB,MULT,DIV,NUM,POW}
class Token
{   
    String text;
    TokenType type;
    int line;
    public Token(TokenType type,String text,int line)
    {
        this.text=text;
        this.type=type;
        this.line=line;
    }
    public String toString()
    {
        return String.format("%s\t|\t%s\t%s\n-----------------------------------",type,text,line);
    }
}
//2222222222222222222222222222222222222222222222222222222222222222222222222222222222
class Identifier
{
    String number,ide,given_token;
    int line,column;
    public Identifier(String given_token,int line)
    {
        this.given_token=given_token;
        this.line=line;
        String temp="";
        for(int j=0;j<given_token.length();j++)
        {   
            if(given_token.charAt(j)=='-')
            {
                temp+='-';
                given_token=given_token.substring(1);
                j--;
                continue;
            }
            else if(given_token.charAt(j)=='+')
            {
                given_token=given_token.substring(1);
                j--;
                continue;
            }
            if(Character.isDigit(given_token.charAt(j)))
            temp+=given_token.charAt(j);
            else
            {
                if(j==0)
                temp+='1';
                this.number=temp;
                this.ide=given_token.substring(j);
                Lexer.ident.add(this.ide);
                break;
            } 
        }
    }
    @Override
     public String toString()
        {
        return String.format("number : %s\n ide : %s\n line : %d\n",number,ide,line);
        }
}


public class Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

ArrayList<Identifier>tokens=new ArrayList();
ArrayList<String>equals=new ArrayList();
static SortedSet<String>ident=new TreeSet<>();

    public static void main(String[] args)throws IOException 
    {
     System.out.println("\n\n\tGenerated Matrix\n");
      FileReader r =new FileReader("in.txt");
        FileWriter fr = new FileWriter("format.in");
        int character;
        while((character=r.read())!=-1)
        {
           if(character==' ')
           continue;
           fr.write(character);
        }
        fr.close();
        r.close();
        r = new FileReader("format.in");
     Lexer l =new Lexer(r);
     // 1111111111111111111111111111111111111111111111111111111111111111111111111111111
     Token tok;
     while ((tok=l.getNextToken()) != null )
      {
         System.out.println(tok);  
      }   
      // 222222222222222222222222222222222222222222222222222222222222222222222222222222
       r.close();  
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private void yy_do_eof () {
		if (false == yy_eof_done) {

    HashMap<String,Integer> hm=new HashMap<String,Integer>();
    Iterator<String> it =ident.iterator();
    int index=0;
    while(it.hasNext())
    {
        String temp=it.next();
        System.out.format("%5s",temp);
        hm.put(temp,index);
        index++;
    }   
    System.out.println("\n");
    int final_size=ident.size();
    for(int i=0;i<tokens.size();i++)
    {
        Identifier temp =tokens.get(i);
        temp.column=hm.get(temp.ide);
        tokens.set(i,temp);
    }
    String[][]matrix=new String[yyline][final_size];
    for(String[] row:matrix)
    {
        Arrays.fill(row,"0");
    }
    for(int i=0;i<tokens.size();i++)
    {
        int r,c;
        r=tokens.get(i).line;
        c=tokens.get(i).column;
        matrix[r][c]=tokens.get(i).number;
    }
    for(int i=0;i<yyline;i++)
    {   
        for(int j=0;j<final_size;j++)
        {
            System.out.format("%5s",matrix[i][j]);
        }
        try{
          if(i==yyline/2)
        {
            System.out.format("%5c%5s\n\n",'=',equals.get(i));
        }
        else
        System.out.format("%10s\n\n",equals.get(i));
       }catch(Exception x)
       {
       System.out.println("\nplease don't leave a blank new line!");
       }
    }
		}
		yy_eof_done = true;
	}
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NO_ANCHOR,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NOT_ACCEPT,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NOT_ACCEPT,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NOT_ACCEPT,
		/* 17 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"8:9,7:2,8,7:2,8:18,7,8:10,5,8,1,8:2,3,2:9,8:3,6,8:3,4:26,8:6,4:26,8:5,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,18,
"0,1,2,3,4,5,6,7,8,9,10,11,9,12,13,1,14,15")[0];

	private int yy_nxt[][] = unpackFromString(16,9,
"1,2,9,15,3,17,4,8,15,-1:11,5,-1,6,-1:6,10:2,3,-1:5,11:3,-1:7,5:2,6,-1:6,13:" +
"2,6,-1:6,14:2,7,-1:11,8,-1:3,12:2,3,-1:6,10:2,-1:7,11:2,-1:7,13:2,-1:7,14:2" +
",-1:7,16:2,7,-1:6,16,-1,7,-1:4");

	public Token getNextToken ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				yy_do_eof();
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 0:
						{}
					case -2:
						break;
					case 1:
						
					case -3:
						break;
					case 2:
						{System.out.println("error");}
					case -4:
						break;
					case 3:
						{tokens.add(new Identifier(yytext(),yyline));}
					case -5:
						break;
					case 4:
						{equals.add(yytext().substring(1));}
					case -6:
						break;
					case 6:
						{tokens.add(new Identifier(yytext(),yyline));}
					case -7:
						break;
					case 7:
						{tokens.add(new Identifier(yytext(),yyline));}
					case -8:
						break;
					case 8:
						{}
					case -9:
						break;
					case 9:
						{System.out.println("error");}
					case -10:
						break;
					case 10:
						{tokens.add(new Identifier(yytext(),yyline));}
					case -11:
						break;
					case 11:
						{equals.add(yytext().substring(1));}
					case -12:
						break;
					case 13:
						{tokens.add(new Identifier(yytext(),yyline));}
					case -13:
						break;
					case 14:
						{tokens.add(new Identifier(yytext(),yyline));}
					case -14:
						break;
					case 15:
						{System.out.println("error");}
					case -15:
						break;
					case 17:
						{System.out.println("error");}
					case -16:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
