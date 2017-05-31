package boot.lab03;

import org.springframework.stereotype.Component;

@Component
public class Calculadora {
	
	public long soma(long x, long y) {
		return x + y;
	}
	
	public long subtracao(long x, long y) {
		return x - y;
	}
	
	public long multiplicacao(long x, long y) {
		return x * y;
	}
	
	public long divisao(long x, long y) {
		return x / y;
	}

}
