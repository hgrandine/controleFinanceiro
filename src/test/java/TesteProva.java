public class TesteProva {
	static void doIt(int x, int y, int m) {
	if (x == 5) {
                m=y;
	} else {
                m=x;
	}
}

public static void main(String[] args) {
	int i=1, j=1, k=9;
	TesteProva.doIt(i,j,k);
	System.out.print(k);
}}